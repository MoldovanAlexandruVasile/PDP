import java.awt.image.BufferedImage;

class Filters {
    private static BufferedImage img;

    Filters(BufferedImage imgBuff) {
        img = imgBuff;
    }

    public BufferedImage getImg() {
        return img;
    }

    void applyBlueFilter(Integer fromHeight, Integer fromWidth,
                         Integer toHeight, Integer toWidth) {
        try {
            for (Integer y = fromHeight; y < toHeight; y++) {
                for (Integer x = fromWidth; x < toWidth; x++) {
                    Integer p = img.getRGB(x, y);
                    Integer a = (p >> 24) & 0xff;
                    Integer b = p & 0xff;
                    p = (a << 24) | (0 << 16) | (0 << 8) | b;
                    img.setRGB(x, y, p);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void applyRedFilter(Integer fromHeight, Integer fromWidth,
                        Integer toHeight, Integer toWidth) {
        try {
            for (Integer y = fromHeight; y < toHeight; y++) {
                for (Integer x = fromWidth; x < toWidth; x++) {
                    Integer p = img.getRGB(x, y);
                    Integer a = (p >> 24) & 0xff;
                    Integer r = (p >> 16) & 0xff;
                    p = (a << 24) | (r << 16) | (0 << 8) | 0;
                    img.setRGB(x, y, p);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void applyGreenFilter(Integer fromHeight, Integer fromWidth,
                                 Integer toHeight, Integer toWidth) {
        try {
            for (Integer y = fromHeight; y < toHeight; y++) {
                for (Integer x = fromWidth; x < toWidth; x++) {
                    int p = img.getRGB(x, y);
                    int a = (p >> 24) & 0xff;
                    int g = (p >> 8) & 0xff;
                    p = (a << 24) | (0 << 16) | (g << 8) | 0;
                    img.setRGB(x, y, p);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void applyNegativeFilter(Integer fromHeight, Integer fromWidth,
                                    Integer toHeight, Integer toWidth) {
        try {
            for (Integer y = fromHeight; y < toHeight; y++) {
                for (Integer x = fromWidth; x < toWidth; x++) {
                    int p = img.getRGB(x, y);
                    int a = (p >> 24) & 0xff;
                    int r = (p >> 16) & 0xff;
                    int g = (p >> 8) & 0xff;
                    int b = p & 0xff;
                    r = 255 - r;
                    g = 255 - g;
                    b = 255 - b;
                    p = (a << 24) | (r << 16) | (g << 8) | b;
                    img.setRGB(x, y, p);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
