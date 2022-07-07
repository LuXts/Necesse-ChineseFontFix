package FontFix.Patches;

import necesse.engine.GameLoadingScreen;
import necesse.engine.GameLog;
import necesse.engine.localization.Localization;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import net.bytebuddy.asm.Advice;
import necesse.gfx.gameFont.*;
import java.io.IOException;
import java.lang.reflect.Method;

@ModMethodPatch(target = FontManager.class, name = "loadFonts", arguments = {})
public class FontManagerLoadFontsPatch {
    @Advice.OnMethodEnter(skipOn = Advice.OnDefaultValue.class)
    static boolean onEnter() {
        FontManager.bit = new GameFontHandler();
        try {
            Class<FontManager> clazz = (Class<FontManager>) Class.forName("necesse.gfx.gameFont.FontManager");
            Method loadTrueTypeFont = clazz.getDeclaredMethod("loadTrueTypeFont", Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, String.class, Integer.TYPE, Integer.TYPE, TrueTypeGameFontInfo[].class);
            loadTrueTypeFont.setAccessible(true);
            try {
                TrueTypeGameFontInfo[] fonts = new TrueTypeGameFontInfo[]{new TrueTypeGameFontInfo("FreeSansBold"),
                        new TrueTypeGameFontInfo("WenQuanYi Micro Hei Regular"),
                        new TrueTypeGameFontInfo("MPlus2"),
                        new TrueTypeGameFontInfo("NanumGothic-Bold"),
                        new TrueTypeGameFontInfo("code2000")};
                FontManager.bit.outlineFonts.add((GameFont) loadTrueTypeFont.invoke(null,12, 1, 3, -1, "bitoutline12", 8, 12, fonts), true);
                FontManager.bit.outlineFonts.add((GameFont) loadTrueTypeFont.invoke(null,16, 2, 2, -2, "bitoutline16", 11, 16, fonts), true);
                FontManager.bit.outlineFonts.add((GameFont) loadTrueTypeFont.invoke(null,20, 2, 1, -3, "bitoutline20", 13, 20, fonts), true);
                FontManager.bit.outlineFonts.add(new TrueTypeGameFont(32, 2, 0, -3, (CustomGameFont.CharArray)null, fonts), false);
                FontManager.bit.regularFonts.add((GameFont) loadTrueTypeFont.invoke(null,12, 0, 2, -1, "bit12", 8, 12, fonts), true);
                FontManager.bit.regularFonts.add((GameFont) loadTrueTypeFont.invoke(null,16, 0, 2, -2, "bit16", 11, 16, fonts), true);
                FontManager.bit.regularFonts.add((GameFont) loadTrueTypeFont.invoke(null,20, 0, 1, -3, "bit20", 13, 20, fonts), true);
                FontManager.bit.regularFonts.add(new TrueTypeGameFont(32, 0, 0, -3, (CustomGameFont.CharArray)null, fonts), false);
            } catch (IOException var2) {
                var2.printStackTrace();
            }

            long time = System.currentTimeMillis();
            if (FontManager.updateFont(Localization.getCurrentLang())) {
                GameLog.debug.println("Initial font update took " + (System.currentTimeMillis() - time) + " ms");
            }
            GameLoadingScreen.font = FontManager.bit;
        } catch (Exception e) {
            GameLog.debug.println(e);
        }
        return false;
    }
}
