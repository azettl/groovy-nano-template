import Template

class templateTest
        extends GroovyTestCase {

    void testBasicTemplate() {

        Template demo = new Template()
        demo.setTemplate('Hello {demooo} {welt}')
        demo.setData([demooo:1, welt:'world'])

        assertEquals('Hello 1 world', demo.render())
    }

}
