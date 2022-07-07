package FontFix;

import necesse.engine.GameLoadingScreen;
import necesse.engine.GameLog;
import necesse.engine.localization.Localization;
import necesse.engine.modLoader.annotations.ModEntry;
import necesse.gfx.gameFont.*;

@ModEntry
public class ChineseFontFix {

    public void preInit() {
        if(Localization.getCurrentLang()==Localization.ChineseSimplified){
            GameLoadingScreen.drawLoadingString("重新加载字体");
        }else if(Localization.getCurrentLang()==Localization.ChineseTraditional){
            GameLoadingScreen.drawLoadingString("重新加載字體");
        }else{
            GameLog.warn.println("Non-Chinese environment detected, ChineseFontFix Mod may have unintended behavior.");
        }
        FontManager.deleteFonts();
        FontManager.loadFonts();
    }
}
