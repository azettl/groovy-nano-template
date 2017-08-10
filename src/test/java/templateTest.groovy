import com.azettl.nano.Template

class templateTest
        extends GroovyTestCase {

    String sTemplate = '<p>\n' +
            '{user.greeting()} {user.first_name} {user.last name}!\n' +
            ' Your account is <strong>{user.account.status}</strong> \n' +
            '{user.nonexistingnode}\n' +
            '</p>'

    String sTemplateTwo = '<p>\n' +
            '{user.greetingTwo()} {user.first_name} {user.last name}!\n' +
            ' Your account is <strong>{user.account.status}</strong> \n' +
            '{user.nonexistingnode}\n' +
            '</p>'

    String sTemplateThree = '<p>\n' +
            '{user.function(2)} {user.first_name} {user.last name}!\n' +
            ' Your account is <strong>{user.account.status}</strong> \n' +
            '{user.nonexistingnode}\n' +
            '</p>'

    String sTemplateFour = '<p>\n' +
            '{user.function("test")} {user.first_name} {user.last name}!\n' +
            ' Your account is <strong>{user.account.status}</strong> \n' +
            '{user.nonexistingnode}\n' +
            '</p>'

    void testCanReplaceString()
    {
        Template demo = new Template()
        demo.setTemplate(sTemplate)
        demo.setData(_getTestData())

        assertEquals(
                '<p>Hello Anon Ymous! Your account is <strong>active</strong> </p>',
                demo.render()
        )
    }

    void testCanSetTemplateFile()
    {
        Template demo = new Template()
        demo.setTemplateFile('src/test/java/template.html')
        demo.setData(_getTestData())

        assertEquals(
                '<p>Hello Anon Ymous! Your account is <strong>active</strong> </p>',
                demo.render()
        )
    }

    void testThrowsErrorWithInvalidTemplateFile()
    {
        Template demo = new Template()
        demo.setData(_getTestData())


        boolean thrown = false
        try{
            demo.setTemplateFile('src/test/java/template.Wrong.html')
        } catch (FileNotFoundException expected ){
            thrown = true
            assertEquals( 'Template file not found', 'Template file not found')
        }
        assertTrue(thrown);
    }

    void testCanReplaceStringAndShowEmpty()
    {
        Template demo = new Template()
        demo.setTemplate(sTemplate)
        demo.setData(_getTestData())
        demo.setShowEmpty(true)

        assertEquals(
                '<p>Hello Anon Ymous! Your account is <strong>active</strong> {user.nonexistingnode}</p>',
                demo.render()
        )
    }

    void testCanReplaceStringWithUnknownFunction()
    {
        Template demo = new Template()
        demo.setTemplate(sTemplateTwo)
        demo.setData(_getTestData())

        assertEquals(
                '<p> Anon Ymous! Your account is <strong>active</strong> </p>',
                demo.render()
        )
    }

    void testCanReplaceStringAndShowEmptyWithUnknownFunction()
    {
        Template demo = new Template()
        demo.setTemplate(sTemplateTwo)
        demo.setData(_getTestData())
        demo.setShowEmpty(true)

        assertEquals(
                '<p>{user.greetingTwo()} Anon Ymous! Your account is <strong>active</strong> {user.nonexistingnode}</p>',
                demo.render()
        )
    }

    void testCanReplaceStringEmptyTemplate()
    {
        Template demo = new Template()
        demo.setData(_getTestData())
        demo.setShowEmpty(true)

        assertEquals(
                '',
                demo.render()
        )
    }

    void testCanReplaceStringEmptyData()
    {
        Template demo = new Template()
        demo.setTemplate(sTemplate)
        demo.setShowEmpty(true)

        assertEquals(
                '<p>{user.greeting()} {user.first_name} {user.last name}! Your account is <strong>{user.account.status}</strong> {user.nonexistingnode}</p>',
                demo.render()
        )
    }

    void testCanReplaceStringAllEmpty()
    {
        Template demo = new Template()

        assertEquals(
                '',
                demo.render()
        )
    }

    void testCanReplaceStringWithFunctionParameterInteger()
    {
        Template demo = new Template()
        demo.setTemplate(sTemplateThree)
        demo.setData(_getTestData())

        assertEquals(
                '<p>Test2 Anon Ymous! Your account is <strong>active</strong> </p>',
                demo.render()
        )
    }

    void testCanReplaceStringWithFunctionParameterString()
    {
        Template demo = new Template()
        demo.setTemplate(sTemplateFour)
        demo.setData(_getTestData())

        assertEquals(
                '<p>Testtest Anon Ymous! Your account is <strong>active</strong> </p>',
                demo.render()
        )
    }

    void testCanGetTemplate()
    {
        Template demo = new Template()
        demo.setTemplate(sTemplateFour)

        assertEquals(
                sTemplateFour,
                demo.getTemplate()
        )
    }

    void testCanGetData()
    {
        Template demo = new Template()
        demo.setData(['test':1])

        assertEquals(
                ['test':1],
                demo.getData()
        )
    }

    void testCanGetShowEmpty()
    {
        Template demo = new Template()
        demo.setShowEmpty(true)

        assertEquals(
                true,
                demo.getShowEmpty()
        )
    }

    private static Map _getTestData(){
        [
            "user":[
                "login":"demo",
                "first_name":"Anon",
                "last name":"Ymous",
                "account":[
                    "status":"active",
                    "expires_at":"2016-12-31"
                ],
                "greeting":{ 'Hello' },
                "function":{ param -> 'Test' + param }
            ]
        ]
    }
}