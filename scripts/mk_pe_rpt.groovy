/* :name = MT -- Create PE report :description=foo
 * 
 * @author      Manuel Souto Pico, Kos Ivantsov
 * @date        2022-06-12
 * @version     0.1.0
 */

/* 
 * @versions: 
 * 0.0.1:   first version
 * 0.0.2:   get all details from the project
 * 0.1.0:   sends successful request to {url}/reports/{id} 
 */

// to be run by the user on the GUI

// dependencies
@Grapes([
	@Grab(group='com.github.groovy-wslite', module='groovy-wslite', version='1.1.3'),
	@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7.1')
])

// imports
import org.omegat.util.StaticUtils
import org.omegat.util.StringUtil
// https://josdem.io/techtalk/groovy/groovy_restclient/
import groovyx.net.http.RESTClient
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.Method.PUT
import static groovyx.net.http.ContentType.JSON
import groovy.json.JsonSlurper
import groovy.json.JsonOutput
import static org.omegat.core.events.IProjectEventListener.PROJECT_CHANGE_TYPE.*
import org.omegat.core.data.PrepareTMXEntry
import org.omegat.core.data.TMXEntry
import javax.swing.JOptionPane;
import org.omegat.util.OConsts;
import java.io.*
import java.nio.file.Files
import java.nio.file.*
import java.nio.file.Path
import java.nio.file.Paths
import org.omegat.gui.main.ProjectUICommands
import org.omegat.core.data.ProjectTMX
import org.omegat.util.OConsts
import groovy.xml.XmlParser
import groovy.xml.XmlSlurper
import groovy.xml.MarkupBuilder 
import groovy.util.*
import java.text.SimpleDateFormat 
import java.util.Date


// url = "http://localhost:8000"
url = "https://xdiff-7p3bqbuxwq-ew.a.run.app"
prop = project.projectProperties
 
// functions
def get_api_key() {

    config_dir = StaticUtils.getConfigDir()
    api_key_file = new File(config_dir + "deepl_api_key.txt")
    if (! api_key_file.exists()) {
        console.println("API key file (deepl_api_key.txt) not found in the configuration folder.")
        return
    }
    String api_key = api_key_file.text
    return api_key
}

def get_reports() {
        
    // try - catch needed

    RESTClient client = new RESTClient(url)
    // def path = "/get"
    def path = "/reports"
    def response
    response = client.get(path: path)
    return response.data
}

def get_report_by_id(id) {
        
    // try - catch needed

    RESTClient client = new RESTClient(url)
    // def path = "/get"
    def path = "/reports/${id}"
    def response
    response = client.get(path: path)
    return response.data
}

def put_report(proj_data) {

    json_obj = JsonOutput.toJson(proj_data)

    // console.println(proj_data.getClass())
    def report_id = proj_data.report_id
    console.println("id: ${report_id}")

    // try{
        def client = new RESTClient(url)
        // def path = "/get"
        def path = "/reports/${report_id}"
        response = client.put(
            path: path,
            contentType: JSON,
            body: json_obj,
            headers: [Accept: 'application/json']
        )
        resp_obj = response
    // } catch(Exception e) {
    //    console.println("Error: ${e.message}")
    // }

    console.println("Status: " + resp_obj.status)
    if (resp_obj.data) {
        console.println("Content Type: " + resp_obj.contentType)
        console.println("Headers: " + resp_obj.getAllHeaders())
        console.println("Body:\n" + JsonOutput.prettyPrint(JsonOutput.toJson(resp_obj.data)))
    }


    return response.data
}

def get_mt_segm_pairs(tmx_file) {

    
    def project_mt = new File(prop.projectInternal, tmx_file)

    def xmlParser = new XmlParser();
    xmlParser.setFeature("http://apache.org/xml/features/disallow-doctype-decl", false);
    xmlParser.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
    def tmx = xmlParser.parse(project_mt);
    // def tmx_body = tmx.body;

    // Get all <tu> that have a translation
    def segm_pairs = [:]
    // get all source texts and create a map where each source
    // text is a key and the list of all its translations is the value
    tmx.body.tu.findAll { node ->
        def source_text = node.tuv[0].seg.text()
        def target_text = node.tuv[1].seg.text()
        segm_pairs[source_text] = target_text
    }

    return segm_pairs // map

}

def pprint(str) {
    def json_str = JsonOutput.toJson(str)
   // console.println(json_str)

    def json_beauty = JsonOutput.prettyPrint(json_str)
    return json_beauty
}


// only run the script when the project is loading (by default it runs three times)
// if (eventType != LOAD) {
//     return
// }

console.println(">>>>> PROJECT LOADING")

// prop: also sometimes called config in the code base
def timestamp = new Date().format("YYYYMMddHHmm")

// create MT map
def mt_segm_pairs = get_mt_segm_pairs("project_mt.tmx")
mt_segm_pairs.each{ console.println("key: $it.key, value: $it.value")}

// objects and clases 

class ProjProps {
    String project_name
    String source_lang
    String target_lang
    Boolean segmentation
}

class Segment {
    String segment_number    
    String file_name
    String source_text
    String target_text
    String original_mt
    Boolean repetition
    Boolean alternative
    String created_by
    Date created_on
    String changed_by
    Date changed_on
    String note
}

class OmegatProject {
    String report_id
    // BigDecimal value
    // Date createdAt
    ProjProps props
    List segments
}

ProjProps props = new ProjProps(
    project_name:   new File(prop.getProjectRoot()).getName().toString(),
    source_lang:    prop.sourceLanguage,
    target_lang:    prop.sourceLanguage,
    segmentation:   prop.isSentenceSegmentingEnabled()
    // autopropagation = prop.isSupportDefaultTranslations()
)

OmegatProject proj = new OmegatProject(
    report_id: props.project_name, 
    // createdAt: new SimpleDateFormat('MM/dd/yyyy').parse('01/01/2018'),
    props: [:],
    segments: []
) 

proj.props = props

// pprint(proj)
// pprint(props)

SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm")

files = project.projectFiles
files.each { file -> 

    file_name = file.filePath.toString()
    file.entries.eachWithIndex { ste, i ->

	   // ste: source text entry
        assert ste == file.entries[i]
        info = project.getTranslationInfo(ste)

        Date creation_date = new Date(info.creationDate) // class java.util.Date
        Date change_date = new Date(info.changeDate) // class java.util.Date

        newPar = ste.paragraphStart ? ste.paragraphStart.toString() : null
        // segmentId = ste.key.id ? ste.key.id : "n/a"
        // isDup = isDup.toString().replaceAll(/NONE/, "0").replaceAll(/FIRST/, "1").replaceAll(/NEXT/, "\"+\"")
               
        Segment segm = new Segment(
            segment_number: ste.entryNum().toString(),
            file_name:      file.filePath.toString(),
            source_text:    ste.getSrcText(),
            target_text:    project.getTranslationInfo(ste) ? project.getTranslationInfo(ste).translation : "",
            original_mt:    mt_segm_pairs[ste.getSrcText()],
            repetition:     ste.getDuplicate(), // isDup
            alternative:    !info.defaultTranslation, // inverted // isAlt
            created_by:     info.creator ? info.creator : "",
            created_on:     creation_date ? creation_date : "", // info.creationDate,
            changed_by:     info.changer ? info.changer : "",
            changed_on:     change_date ? change_date : "", // info.changeDate,
            note:           info.note ? info.note : ""  // info.getNote() ???
        ) 

        proj.segments.add(segm)
    }   
}

// def data = get_reports()
// def data = get_report_by_id("MT_test02_OMT")
// def data = put_report(json)
// x = pprint(proj)
// console.println(x)

def data = put_report(proj) 
// console.println(data)
return

/* 
 * @todo: 
 * fail elegantly if not project_mt.tmx is found
 * get url of the xdiff report and open it in the browser
 * detect whether there's a tab in the browswer showing that page and refresh (?)
 * add project_mt.tmx to /tm/auto or /tmx/tmx2source/MT.tmx (in which case, target language must be "mt")
 * write final message in the status bar: success or fail
 * check that a project is open to run
 */
