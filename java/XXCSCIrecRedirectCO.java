/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package xxcsc.oracle.apps.ar.irec.webui;

import java.io.IOException;
import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.OAViewObject;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.fnd.framework.OAFwkConstants;
import xxcsc.oracle.apps.ar.irec.server.XXCSCiRecRedirectAMImpl;


/**
 * Controller for ...
 */
public class XXCSCIrecRedirectCO extends OAControllerImpl
{
  public static final String RCS_ID="$Header$";
  public static final boolean RCS_ID_RECORDED =
        VersionInfo.recordClassVersion(RCS_ID, "%packagename%");

  /**
   * Layout and page setup logic for a region.
   * @param pageContext the current OA page context
   * @param webBean the web bean corresponding to the region
   */
  public void processRequest(OAPageContext pageContext, OAWebBean webBean)
  {
    super.processRequest(pageContext, webBean);
    
      // Redirect to Angular iReceivable
      // AOL Function - XXCSC_IREC_REDIRECT_FNC
       // Redirect 
       // AOL Function - XXCSC_IREC_REDIRECT_FNC XXCSC_ARI_MENU_INTERNAL
             try {
                   String currentURL=null;// "https://mydev.cscglobal.com/cscirec/irecinterface";
                   XXCSCiRecRedirectAMImpl am= (XXCSCiRecRedirectAMImpl)pageContext.getApplicationModule(webBean);   
                   OAViewObject viewObj = am.getXXCSCFndLookUpValueVO1();
                   if(viewObj!=null){
                      viewObj.setWhereClauseParam(0,"IREC_URL");
                      viewObj.executeQuery();
                        if(viewObj.getRowCount()>0){
                        //System.out.println("test");
                           currentURL=viewObj.first().getAttribute("Meaning").toString();                 
                         }
                        else
                        {
                           // System.out.println("test1");
                          //  am.writeDiagnostics(am,"LookUP code IREC_URL is not available in lookup XXCSC_IREC_GENERIC_LOOKUP" 
                                    //           , OAFwkConstants.ERROR);
                            throw new OAException("LookUP Code IREC_URL is not available in lookup XXCSC_IREC_GENERIC_LOOKUP",OAException.WARNING);         
                        }

               }
               
               
                   //"https://mydev.cscglobal.com/cscirec/irecinterface";
                   pageContext.sendRedirect(currentURL);
             }
             catch(IOException e)
                {
                  System.err.println(e);
                }  

         
  }

  /**
   * Procedure to handle form submissions for form elements in
   * a region.
   * @param pageContext the current OA page context
   * @param webBean the web bean corresponding to the region
   */
  public void processFormRequest(OAPageContext pageContext, OAWebBean webBean)
  {
    super.processFormRequest(pageContext, webBean);
  }

}
