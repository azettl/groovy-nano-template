package com.azettl.nano

import org.codehaus.groovy.runtime.NullObject

/**
 * The groovy-nano-template class replaces placeholders in a string with values from an array.
 *
 * @author   Andreas Zettl <info@azettl.net>
 * @url      https://github.com/azettl/groovy-nano-template
 */
class Template {

    private String  sTemplate  = ''
    private Map     aData      = [:]
    private Boolean bShowEmpty = false

    /**
     * This method is used to set the template string in which
     * the placeholders should get replaced.
     * @param sUserTemplate
     */
    void setTemplate(String sUserTemplate)
    {
        sTemplate = sUserTemplate
    }

    /**
     * This method is used to set the template from a relative
     * path.
     * @param sRelativePathToFile
     */
    void setTemplateFile(String sRelativePathToFile)
    {
        def file = new File(sRelativePathToFile)

        if(!file.isFile()) {
            throw new FileNotFoundException('Template file not found.')
        }

        setTemplate(file.text)
    }

    /**
     * This method is used to return the current template string.
     *
     * @return
     */
    String getTemplate()
    {
        sTemplate
    }

    /**
     * This method is used to set the data array in which
     * the data for the placeholders is stored.
     * @param aUserData
     */
    void setData(Map aUserData)
    {
        aData = aUserData
    }

    /**
     * This method is used to get the current data array.
     * @return
     */
    Map getData()
    {
        aData
    }

    /**
     * This method is used to set whether placeholders which could not
     * be replaced shall remain in the output string or not.
     * @param bUserShowEmpty
     */
    void setShowEmpty(Boolean bUserShowEmpty)
    {
        bShowEmpty = bUserShowEmpty
    }

    /**
     * This method is used to get the current show empty placeholder
     * status.
     * @return
     */
    Boolean getShowEmpty()
    {
        bShowEmpty
    }

    /**
     * This method replaces the placeholders in the template string with
     * the values from the data object and returns the new string.
     * @return
     */
    String render(){
        getTemplate().replaceAll(
                /\{(.*?)\}/,
                { word ->
                    List aToSearch   = word[1].tokenize('.')
                    Object aSearchIn = getData()
                    def sValue       = ''
                    def mParameter   = ''

                    aToSearch.each { sKey ->

                        String sFormattedKey = sKey.replace('()', '')
                        if(getFunctionParameter(sKey as String) instanceof List){

                            List aFormattedKey = getFunctionParameter(sKey as String)

                            if(aFormattedKey[0] instanceof List){
                                mParameter    = aFormattedKey[0][1]
                                sFormattedKey = sKey.replace(aFormattedKey[0][0], '')
                            }
                        }

                        sValue          = aSearchIn[sFormattedKey]
                        def mValueClass = sValue.getClass()

                        if(mValueClass != String && mValueClass != LinkedHashMap && mValueClass != NullObject){

                            if(mParameter){
                                mParameter = mParameter.replaceAll(/^["\'](.*)["\']$/, { res -> res[1] })
                                sValue     = sValue(mParameter as String)
                                return
                            }

                            sValue = sValue()
                            return
                        }else if(mValueClass == NullObject){
                            if(!getShowEmpty()){
                                sValue = ''
                                return
                            }

                            sValue = word[0]
                            return
                        }

                        aSearchIn = sValue
                    }

                    sValue
                }
        ).replaceAll(/^\s+|\n|\r|\t/, '')
    }

    /**
     * Return a List with the function parameters.
     *
     * @param String sKey   the key including the function parameter
     * @return List
     */
    private static List getFunctionParameter(String sKey)
    {
        sKey.findAll(/\((.*?)\)/){ res -> res }
    }
}