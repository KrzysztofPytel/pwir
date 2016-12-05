package Swing4;
import java.util.Random;

class ZrobmySobieStringa {

    Random r;
    private String string = "";
    private static final char[] tablicaZnakow = {'優', '劣', 'や', '善', '悪', 'に', 'よ',
        +'っ', 'て', '選', '出', 'さ', 'れ', 'て', 'い', 'ケ', 'コ', 'イ', 'ウ',
        +'エ', 'オ', 'ジ', 'ャ', 'な', 'な', '記', '事', 'に', '選', 'ば', 'れ', 'て', 'い'
        + 'る', 'み', 'ん', 'な', 'の', '感', '想', 'を', '見', 'る', 'こ', 'と', 'が', 'で', 'き'};

    ZrobmySobieStringa() {
        r = new Random();
    }

    public String tworz() {

        String string = "";
        char sign;

        for (int i = 0; i < r.nextInt(40 - 20 + 1) + 20; i++) {
            sign = tablicaZnakow[r.nextInt(tablicaZnakow.length)];
            string += Character.toString(sign);
        }
        return string;
    }

    public String pomieszaj(int dlugosc) {
        String stringPomieszany = "";
        char sign;

        for (int i = 0; i < dlugosc; i++) {
            sign = tablicaZnakow[r.nextInt(tablicaZnakow.length)];
            stringPomieszany += Character.toString(sign);
        }
        return stringPomieszany;
    }
}

