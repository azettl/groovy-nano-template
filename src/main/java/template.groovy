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
        getTemplate().replaceAll(
                /\{(.*?)\}/,
                { word ->
                    List aToSearch = word[1].tokenize('.')
                    Object aSearchIn = getData()
                    def sValue       = ''
                    def mParameter   = ''

                    aToSearch.each { sKey ->

                        String sFormattedKey = sKey.replace('()', '')
                        if(getFunctionNameAndParameter(sKey) instanceof List){

                            List aFormattedKey   = getFunctionNameAndParameter(sKey)

                            if(aFormattedKey[0] instanceof List){
                                mParameter = aFormattedKey[0][1]
                                sFormattedKey = sKey.replace(aFormattedKey[0][0], '')
                            }
                        }

                        sValue = aSearchIn[sFormattedKey]

                        if(sValue instanceof String){
                            return
                        }else if (sValue.getClass() != java.util.LinkedHashMap
                                && sValue.getClass() != org.codehaus.groovy.runtime.NullObject){
                            if(mParameter){
                                mParameter = mParameter.replaceAll(/^["\'](.*)["\']$/, { res -> res[1] })
                                sValue = sValue(mParameter)
                            } else {
                                sValue = sValue()
                            }
                            return
                        }else if(!getShowEmpty() && sValue.getClass() == org.codehaus.groovy.runtime.NullObject){
                            sValue = ''
                            return
                        }else if(getShowEmpty() && sValue.getClass() == org.codehaus.groovy.runtime.NullObject){
                            sValue = word[0]
                            return
                        }

                        aSearchIn = sValue
                    }

                    sValue
                }
        ).replaceAll(/^\s+|\n|\r|\t/, '')
    }

    private static List getFunctionNameAndParameter(String sKey)
    {
        sKey.findAll(/\((.*?)\)/){ res -> res }
    }
}