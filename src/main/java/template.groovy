class Template {

    private String  sTemplate  = ''
    private Map     aData      = [:]
    private Boolean bShowEmpty = false
    private String  sOutput    = ''

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

        sOutput
    }
}