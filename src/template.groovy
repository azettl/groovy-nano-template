class Template {

    private String  sTemplate  = ''
    private Map     aData      = [:]
    private Boolean bShowEmpty = false
    private String  sOutput    = ''

    Template(){

    }

    void setTemplate(String sUserTemplate)
    {
        sTemplate = sUserTemplate
    }

    String getTemplate()
    {
        sTemplate
    }

    void setData(Map aUserData)
    {
        aData = aUserData
    }

    Map getData()
    {
        aData
    }

    void setShowEmpty(Boolean bUserShowEmpty)
    {
        bShowEmpty = bUserShowEmpty
    }

    Boolean getShowEmpty()
    {
        bShowEmpty
    }

    String render(){
        sOutput = sTemplate.replaceAll(
                /\{(.*?)\}/,
                { word -> aData[word[1]] } )

        println(sOutput)
    }
}


Template demo = new Template()
demo.setTemplate('Hello {demooo} {welt}')
demo.setData([demooo:1, welt:'world'])
demo.render()