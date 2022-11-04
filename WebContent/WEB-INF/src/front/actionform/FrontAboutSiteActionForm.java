package front.actionform;

import org.apache.struts.validator.ValidatorForm;

/**
 * サイトについて画面で使用するデータを格納するためのアクションフォーム
 */
public class FrontAboutSiteActionForm extends ValidatorForm {

    String siteUrl; // サイトURL

    public FrontAboutSiteActionForm() {}

    /**
     * siteUrlを取得する。
     */
    public String getSiteUrl() {
        return siteUrl;
    }

    /**
     * siteUrlを格納する。
     */
    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

}
