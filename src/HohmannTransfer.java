import java.util.HashMap;
import java.util.Map;

public class HohmannTransfer {
    public int planet;
    public static void main(String[] args) {
        Interface itf;
        itf = new Interface();
        itf.setVisible(true);
//        visviva("Kerbin", 80000, 80000, 2863334, 2863334);
    }

    static double visviva(String body, double ap, double pe, double tap, double tpe) {
        double GM = 0;
        double rad = 0;

        switch (body) {
            case "Kerbol" : GM = Const.GM[0];  rad = Const.rad[0];  break;
            case "Moho"   : GM = Const.GM[1];  rad = Const.rad[1];  break;
            case "Eve"    : GM = Const.GM[2];  rad = Const.rad[2];  break;
            case "Gilly"  : GM = Const.GM[3];  rad = Const.rad[3];  break;
            case "Kerbin" : GM = Const.GM[4];  rad = Const.rad[4];  break;
            case "Mun"    : GM = Const.GM[5];  rad = Const.rad[5];  break;
            case "Minmus" : GM = Const.GM[6];  rad = Const.rad[6];  break;
            case "Duna"   : GM = Const.GM[7];  rad = Const.rad[7];  break;
            case "Ike"    : GM = Const.GM[8];  rad = Const.rad[8];  break;
            case "Dres"   : GM = Const.GM[9];  rad = Const.rad[9];  break;
            case "Jool"   : GM = Const.GM[10]; rad = Const.rad[10]; break;
            case "Laythe" : GM = Const.GM[11]; rad = Const.rad[11]; break;
            case "Vall"   : GM = Const.GM[12]; rad = Const.rad[12]; break;
            case "Tylo"   : GM = Const.GM[13]; rad = Const.rad[13]; break;
            case "Bop"    : GM = Const.GM[14]; rad = Const.rad[14]; break;
            case "Pol"    : GM = Const.GM[15]; rad = Const.rad[15]; break;
            case "Sarnus" : GM = Const.GM[16]; rad = Const.rad[16]; break;
        }

        System.out.println(   Math.sqrt(GM * ( (2 /  pe + rad) - ( 1/ ( (pe  +  ap + 2 * rad) / 2 ) ) ) )  );

        double v1 = Math.sqrt(GM * ((2 / ( pe + rad)) - (1/((pe  +  ap + 2 * rad)/2))));   //velocity at initial periapsis
        double v2 = Math.sqrt(GM * ((2 / ( pe + rad)) - (1/((pe  + tap + 2 * rad)/2))));   //velocity at periapsis after burn
        double v3 = Math.sqrt(GM * ((2 / (tap + rad)) - (1/((pe  + tap + 2 * rad)/2))));   //velocity at target apoapsis
        double v4 = Math.sqrt(GM * ((2 / (tap + rad)) - (1/((tpe + tap + 2 * rad)/2))));   //velocity in final orbit

        double firstBurn = v2 - v1;
        double secondBurn = v4 - v3;

        System.out.printf("First Burn: %fm/s %nSecond Burn: %fm/s %nTotal delta-v:%fm/s %nHappy flying!", firstBurn, secondBurn, firstBurn + secondBurn);
        return firstBurn + secondBurn;
    }
}

class Const {
    static double[] GM = {
            1.1723328 * Math.pow(10, 18), //KerbolGM
            1.6860938 * Math.pow(10, 11), //MohoGM
            8.1717302 * Math.pow(10, 12), //EveGM
            8289449.8                   , //GillyGM
            3.5316000 * Math.pow(10, 12), //KerbinGM
            6.5138398 * Math.pow(10, 10), //MunGM
            1.7658000 * Math.pow(10, 9),  //MinmusGM
            3.0136321 * Math.pow(10, 11), //DunaGM
            1.8568369 * Math.pow(10, 10), //IkeGM
            2.1484489 * Math.pow(10, 10), //DresGM
            2.8252800 * Math.pow(10, 14), //JoolGM
            1.9620000 * Math.pow(10, 12), //LaytheGM
            2.0748150 * Math.pow(10, 11), //VallGM
            2.8252800 * Math.pow(10, 12), //TyloGM
            2.4868349 * Math.pow(10, 9),  //BopGM
            7.2170208 * Math.pow(10, 8),  //PolGM
            8.2089702 * Math.pow(10, 13)  //SarnusGM
    };
    static double[] rad = {
            261600000, //KerbolRad
            250000,    //MohoRad
            700000,    //EveRad
            13000,     //GillyRad
            600000,    //KerbinRad
            200000,    //MunRad
            60000,     //MinmusRad
            320000,    //DunaRad
            130000,    //IkeRad
            138000,    //DresRad
            6000000,   //JoolRad
            500000,    //LaytheRad
            300000,    //VallRad
            600000,    //TyloRad
            65000,     //BopRad
            44000,     //PolRad
            5300000,   //SarnusRad
    };
}
// keostationary == 2,863,334m