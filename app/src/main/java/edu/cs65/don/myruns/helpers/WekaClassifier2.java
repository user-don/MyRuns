// Generated with Weka 3.8.0
//
// This code is public domain and comes with no warranty.
//
// Timestamp: Fri May 06 17:11:48 EDT 2016

package edu.cs65.don.myruns.helpers;

class WekaClassifier2 {

    public static double classify(Object[] i)
            throws Exception {

        double p = Double.NaN;
        p = WekaClassifier2.Nac99fe10(i);
        return p;
    }
    static double Nac99fe10(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 0;
        } else if (((Double) i[0]).doubleValue() <= 100.59138) {
            p = WekaClassifier2.N7a6bb41a1(i);
        } else if (((Double) i[0]).doubleValue() > 100.59138) {
            p = WekaClassifier2.N7a8777406(i);
        }
        return p;
    }
    static double N7a6bb41a1(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 3;
        } else if (((Double) i[0]).doubleValue() <= 12.258446) {
            p = 3;
        } else if (((Double) i[0]).doubleValue() > 12.258446) {
            p = WekaClassifier2.N60f744b62(i);
        }
        return p;
    }
    static double N60f744b62(Object []i) {
        double p = Double.NaN;
        if (i[21] == null) {
            p = 0;
        } else if (((Double) i[21]).doubleValue() <= 0.823326) {
            p = 0;
        } else if (((Double) i[21]).doubleValue() > 0.823326) {
            p = WekaClassifier2.N419353d53(i);
        }
        return p;
    }
    static double N419353d53(Object []i) {
        double p = Double.NaN;
        if (i[27] == null) {
            p = 0;
        } else if (((Double) i[27]).doubleValue() <= 2.251054) {
            p = WekaClassifier2.N7e3ea914(i);
        } else if (((Double) i[27]).doubleValue() > 2.251054) {
            p = 3;
        }
        return p;
    }
    static double N7e3ea914(Object []i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 0;
        } else if (((Double) i[1]).doubleValue() <= 21.684143) {
            p = WekaClassifier2.N208f55135(i);
        } else if (((Double) i[1]).doubleValue() > 21.684143) {
            p = 1;
        }
        return p;
    }
    static double N208f55135(Object []i) {
        double p = Double.NaN;
        if (i[10] == null) {
            p = 0;
        } else if (((Double) i[10]).doubleValue() <= 2.191312) {
            p = 0;
        } else if (((Double) i[10]).doubleValue() > 2.191312) {
            p = 2;
        }
        return p;
    }
    static double N7a8777406(Object []i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 1;
        } else if (((Double) i[2]).doubleValue() <= 235.340565) {
            p = WekaClassifier2.N21d19e3e7(i);
        } else if (((Double) i[2]).doubleValue() > 235.340565) {
            p = WekaClassifier2.N17dbe9cd65(i);
        }
        return p;
    }
    static double N21d19e3e7(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 1;
        } else if (((Double) i[0]).doubleValue() <= 993.668297) {
            p = WekaClassifier2.N39fe5ff78(i);
        } else if (((Double) i[0]).doubleValue() > 993.668297) {
            p = WekaClassifier2.N1938885253(i);
        }
        return p;
    }
    static double N39fe5ff78(Object []i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 1;
        } else if (((Double) i[1]).doubleValue() <= 247.07856) {
            p = WekaClassifier2.N6383b2169(i);
        } else if (((Double) i[1]).doubleValue() > 247.07856) {
            p = WekaClassifier2.N9bc787149(i);
        }
        return p;
    }
    static double N6383b2169(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 1;
        } else if (((Double) i[0]).doubleValue() <= 171.506451) {
            p = WekaClassifier2.N227ff45110(i);
        } else if (((Double) i[0]).doubleValue() > 171.506451) {
            p = WekaClassifier2.N2357289f18(i);
        }
        return p;
    }
    static double N227ff45110(Object []i) {
        double p = Double.NaN;
        if (i[16] == null) {
            p = 1;
        } else if (((Double) i[16]).doubleValue() <= 6.13779) {
            p = WekaClassifier2.Nf72ae4e11(i);
        } else if (((Double) i[16]).doubleValue() > 6.13779) {
            p = 3;
        }
        return p;
    }
    static double Nf72ae4e11(Object []i) {
        double p = Double.NaN;
        if (i[32] == null) {
            p = 1;
        } else if (((Double) i[32]).doubleValue() <= 1.193266) {
            p = WekaClassifier2.N44b859b012(i);
        } else if (((Double) i[32]).doubleValue() > 1.193266) {
            p = WekaClassifier2.N6bf8846217(i);
        }
        return p;
    }
    static double N44b859b012(Object []i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 1;
        } else if (((Double) i[2]).doubleValue() <= 11.163945) {
            p = 1;
        } else if (((Double) i[2]).doubleValue() > 11.163945) {
            p = WekaClassifier2.N3ad5403313(i);
        }
        return p;
    }
    static double N3ad5403313(Object []i) {
        double p = Double.NaN;
        if (i[30] == null) {
            p = 1;
        } else if (((Double) i[30]).doubleValue() <= 0.568986) {
            p = WekaClassifier2.N6755e64414(i);
        } else if (((Double) i[30]).doubleValue() > 0.568986) {
            p = WekaClassifier2.N7bbc97fe16(i);
        }
        return p;
    }
    static double N6755e64414(Object []i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 1;
        } else if (((Double) i[2]).doubleValue() <= 32.959956) {
            p = WekaClassifier2.N7d4f343e15(i);
        } else if (((Double) i[2]).doubleValue() > 32.959956) {
            p = 0;
        }
        return p;
    }
    static double N7d4f343e15(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 0;
        } else if (((Double) i[0]).doubleValue() <= 109.345226) {
            p = 0;
        } else if (((Double) i[0]).doubleValue() > 109.345226) {
            p = 1;
        }
        return p;
    }
    static double N7bbc97fe16(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 0;
        } else if (((Double) i[0]).doubleValue() <= 153.226857) {
            p = 0;
        } else if (((Double) i[0]).doubleValue() > 153.226857) {
            p = 1;
        }
        return p;
    }
    static double N6bf8846217(Object []i) {
        double p = Double.NaN;
        if (i[13] == null) {
            p = 2;
        } else if (((Double) i[13]).doubleValue() <= 2.655547) {
            p = 2;
        } else if (((Double) i[13]).doubleValue() > 2.655547) {
            p = 1;
        }
        return p;
    }
    static double N2357289f18(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 1;
        } else if (((Double) i[0]).doubleValue() <= 657.979258) {
            p = WekaClassifier2.N27bd160f19(i);
        } else if (((Double) i[0]).doubleValue() > 657.979258) {
            p = WekaClassifier2.Nfb73cde39(i);
        }
        return p;
    }
    static double N27bd160f19(Object []i) {
        double p = Double.NaN;
        if (i[3] == null) {
            p = 1;
        } else if (((Double) i[3]).doubleValue() <= 115.067398) {
            p = WekaClassifier2.N38a1666120(i);
        } else if (((Double) i[3]).doubleValue() > 115.067398) {
            p = WekaClassifier2.N1bdd56cf36(i);
        }
        return p;
    }
    static double N38a1666120(Object []i) {
        double p = Double.NaN;
        if (i[6] == null) {
            p = 1;
        } else if (((Double) i[6]).doubleValue() <= 34.812701) {
            p = WekaClassifier2.N17d53e8321(i);
        } else if (((Double) i[6]).doubleValue() > 34.812701) {
            p = WekaClassifier2.N11b2085535(i);
        }
        return p;
    }
    static double N17d53e8321(Object []i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 1;
        } else if (((Double) i[2]).doubleValue() <= 87.986261) {
            p = WekaClassifier2.Nddcff3622(i);
        } else if (((Double) i[2]).doubleValue() > 87.986261) {
            p = WekaClassifier2.N4f20702225(i);
        }
        return p;
    }
    static double Nddcff3622(Object []i) {
        double p = Double.NaN;
        if (i[11] == null) {
            p = 1;
        } else if (((Double) i[11]).doubleValue() <= 1.270677) {
            p = WekaClassifier2.N429e9dec23(i);
        } else if (((Double) i[11]).doubleValue() > 1.270677) {
            p = 1;
        }
        return p;
    }
    static double N429e9dec23(Object []i) {
        double p = Double.NaN;
        if (i[11] == null) {
            p = 1;
        } else if (((Double) i[11]).doubleValue() <= 1.226868) {
            p = WekaClassifier2.N71bf69b024(i);
        } else if (((Double) i[11]).doubleValue() > 1.226868) {
            p = 2;
        }
        return p;
    }
    static double N71bf69b024(Object []i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 1;
        } else if (((Double) i[5]).doubleValue() <= 26.528522) {
            p = 1;
        } else if (((Double) i[5]).doubleValue() > 26.528522) {
            p = 2;
        }
        return p;
    }
    static double N4f20702225(Object []i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 1;
        } else if (((Double) i[7]).doubleValue() <= 7.595961) {
            p = WekaClassifier2.N25ef13bf26(i);
        } else if (((Double) i[7]).doubleValue() > 7.595961) {
            p = WekaClassifier2.N19d0922f28(i);
        }
        return p;
    }
    static double N25ef13bf26(Object []i) {
        double p = Double.NaN;
        if (i[10] == null) {
            p = 2;
        } else if (((Double) i[10]).doubleValue() <= 3.120017) {
            p = 2;
        } else if (((Double) i[10]).doubleValue() > 3.120017) {
            p = WekaClassifier2.N13288b1327(i);
        }
        return p;
    }
    static double N13288b1327(Object []i) {
        double p = Double.NaN;
        if (i[6] == null) {
            p = 3;
        } else if (((Double) i[6]).doubleValue() <= 14.0084) {
            p = 3;
        } else if (((Double) i[6]).doubleValue() > 14.0084) {
            p = 1;
        }
        return p;
    }
    static double N19d0922f28(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 1;
        } else if (((Double) i[0]).doubleValue() <= 514.643411) {
            p = WekaClassifier2.N553d505729(i);
        } else if (((Double) i[0]).doubleValue() > 514.643411) {
            p = WekaClassifier2.N5fb5b1b631(i);
        }
        return p;
    }
    static double N553d505729(Object []i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 1;
        } else if (((Double) i[2]).doubleValue() <= 96.380941) {
            p = WekaClassifier2.N892c3a430(i);
        } else if (((Double) i[2]).doubleValue() > 96.380941) {
            p = 1;
        }
        return p;
    }
    static double N892c3a430(Object []i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 1;
        } else if (((Double) i[4]).doubleValue() <= 32.404861) {
            p = 1;
        } else if (((Double) i[4]).doubleValue() > 32.404861) {
            p = 2;
        }
        return p;
    }
    static double N5fb5b1b631(Object []i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 2;
        } else if (((Double) i[4]).doubleValue() <= 38.161946) {
            p = WekaClassifier2.N4269018532(i);
        } else if (((Double) i[4]).doubleValue() > 38.161946) {
            p = 1;
        }
        return p;
    }
    static double N4269018532(Object []i) {
        double p = Double.NaN;
        if (i[17] == null) {
            p = 2;
        } else if (((Double) i[17]).doubleValue() <= 5.690216) {
            p = WekaClassifier2.N7d695e7733(i);
        } else if (((Double) i[17]).doubleValue() > 5.690216) {
            p = 2;
        }
        return p;
    }
    static double N7d695e7733(Object []i) {
        double p = Double.NaN;
        if (i[10] == null) {
            p = 2;
        } else if (((Double) i[10]).doubleValue() <= 10.362339) {
            p = WekaClassifier2.N63f82d9d34(i);
        } else if (((Double) i[10]).doubleValue() > 10.362339) {
            p = 1;
        }
        return p;
    }
    static double N63f82d9d34(Object []i) {
        double p = Double.NaN;
        if (i[8] == null) {
            p = 1;
        } else if (((Double) i[8]).doubleValue() <= 6.095599) {
            p = 1;
        } else if (((Double) i[8]).doubleValue() > 6.095599) {
            p = 2;
        }
        return p;
    }
    static double N11b2085535(Object []i) {
        double p = Double.NaN;
        if (i[15] == null) {
            p = 1;
        } else if (((Double) i[15]).doubleValue() <= 29.971965) {
            p = 1;
        } else if (((Double) i[15]).doubleValue() > 29.971965) {
            p = 3;
        }
        return p;
    }
    static double N1bdd56cf36(Object []i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 2;
        } else if (((Double) i[7]).doubleValue() <= 33.363655) {
            p = WekaClassifier2.N2cf0392537(i);
        } else if (((Double) i[7]).doubleValue() > 33.363655) {
            p = 1;
        }
        return p;
    }
    static double N2cf0392537(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 1;
        } else if (((Double) i[0]).doubleValue() <= 628.932529) {
            p = WekaClassifier2.Ne466de338(i);
        } else if (((Double) i[0]).doubleValue() > 628.932529) {
            p = 2;
        }
        return p;
    }
    static double Ne466de338(Object []i) {
        double p = Double.NaN;
        if (i[10] == null) {
            p = 2;
        } else if (((Double) i[10]).doubleValue() <= 4.269049) {
            p = 2;
        } else if (((Double) i[10]).doubleValue() > 4.269049) {
            p = 1;
        }
        return p;
    }
    static double Nfb73cde39(Object []i) {
        double p = Double.NaN;
        if (i[6] == null) {
            p = 3;
        } else if (((Double) i[6]).doubleValue() <= 40.539289) {
            p = WekaClassifier2.N7ead78c940(i);
        } else if (((Double) i[6]).doubleValue() > 40.539289) {
            p = 1;
        }
        return p;
    }
    static double N7ead78c940(Object []i) {
        double p = Double.NaN;
        if (i[8] == null) {
            p = 3;
        } else if (((Double) i[8]).doubleValue() <= 30.278137) {
            p = WekaClassifier2.N53395f2841(i);
        } else if (((Double) i[8]).doubleValue() > 30.278137) {
            p = 1;
        }
        return p;
    }
    static double N53395f2841(Object []i) {
        double p = Double.NaN;
        if (i[3] == null) {
            p = 3;
        } else if (((Double) i[3]).doubleValue() <= 38.890014) {
            p = 3;
        } else if (((Double) i[3]).doubleValue() > 38.890014) {
            p = WekaClassifier2.N6e2d19d942(i);
        }
        return p;
    }
    static double N6e2d19d942(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 1;
        } else if (((Double) i[0]).doubleValue() <= 760.344805) {
            p = WekaClassifier2.N58ca990e43(i);
        } else if (((Double) i[0]).doubleValue() > 760.344805) {
            p = WekaClassifier2.N5e06d64c47(i);
        }
        return p;
    }
    static double N58ca990e43(Object []i) {
        double p = Double.NaN;
        if (i[10] == null) {
            p = 3;
        } else if (((Double) i[10]).doubleValue() <= 6.13618) {
            p = 3;
        } else if (((Double) i[10]).doubleValue() > 6.13618) {
            p = WekaClassifier2.N4db4c9ee44(i);
        }
        return p;
    }
    static double N4db4c9ee44(Object []i) {
        double p = Double.NaN;
        if (i[17] == null) {
            p = 1;
        } else if (((Double) i[17]).doubleValue() <= 7.410647) {
            p = WekaClassifier2.N546d1f6f45(i);
        } else if (((Double) i[17]).doubleValue() > 7.410647) {
            p = WekaClassifier2.N520827ea46(i);
        }
        return p;
    }
    static double N546d1f6f45(Object []i) {
        double p = Double.NaN;
        if (i[8] == null) {
            p = 2;
        } else if (((Double) i[8]).doubleValue() <= 11.517412) {
            p = 2;
        } else if (((Double) i[8]).doubleValue() > 11.517412) {
            p = 1;
        }
        return p;
    }
    static double N520827ea46(Object []i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 3;
        } else if (((Double) i[4]).doubleValue() <= 43.735601) {
            p = 3;
        } else if (((Double) i[4]).doubleValue() > 43.735601) {
            p = 2;
        }
        return p;
    }
    static double N5e06d64c47(Object []i) {
        double p = Double.NaN;
        if (i[14] == null) {
            p = 2;
        } else if (((Double) i[14]).doubleValue() <= 4.129382) {
            p = WekaClassifier2.N35168fdb48(i);
        } else if (((Double) i[14]).doubleValue() > 4.129382) {
            p = 3;
        }
        return p;
    }
    static double N35168fdb48(Object []i) {
        double p = Double.NaN;
        if (i[14] == null) {
            p = 3;
        } else if (((Double) i[14]).doubleValue() <= 1.989292) {
            p = 3;
        } else if (((Double) i[14]).doubleValue() > 1.989292) {
            p = 2;
        }
        return p;
    }
    static double N9bc787149(Object []i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 2;
        } else if (((Double) i[5]).doubleValue() <= 84.21576) {
            p = WekaClassifier2.N1a1839db50(i);
        } else if (((Double) i[5]).doubleValue() > 84.21576) {
            p = 1;
        }
        return p;
    }
    static double N1a1839db50(Object []i) {
        double p = Double.NaN;
        if (i[3] == null) {
            p = 3;
        } else if (((Double) i[3]).doubleValue() <= 29.606571) {
            p = 3;
        } else if (((Double) i[3]).doubleValue() > 29.606571) {
            p = WekaClassifier2.N7244286251(i);
        }
        return p;
    }
    static double N7244286251(Object []i) {
        double p = Double.NaN;
        if (i[3] == null) {
            p = 2;
        } else if (((Double) i[3]).doubleValue() <= 54.233075) {
            p = 2;
        } else if (((Double) i[3]).doubleValue() > 54.233075) {
            p = WekaClassifier2.N73ad19a652(i);
        }
        return p;
    }
    static double N73ad19a652(Object []i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 3;
        } else if (((Double) i[2]).doubleValue() <= 131.479989) {
            p = 3;
        } else if (((Double) i[2]).doubleValue() > 131.479989) {
            p = 2;
        }
        return p;
    }
    static double N1938885253(Object []i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 3;
        } else if (((Double) i[2]).doubleValue() <= 153.273106) {
            p = WekaClassifier2.N42b5bc4f54(i);
        } else if (((Double) i[2]).doubleValue() > 153.273106) {
            p = WekaClassifier2.N38052a8558(i);
        }
        return p;
    }
    static double N42b5bc4f54(Object []i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 3;
        } else if (((Double) i[2]).doubleValue() <= 84.005435) {
            p = 3;
        } else if (((Double) i[2]).doubleValue() > 84.005435) {
            p = WekaClassifier2.N11e18b4355(i);
        }
        return p;
    }
    static double N11e18b4355(Object []i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 3;
        } else if (((Double) i[2]).doubleValue() <= 121.294131) {
            p = WekaClassifier2.Na1ac0f056(i);
        } else if (((Double) i[2]).doubleValue() > 121.294131) {
            p = 3;
        }
        return p;
    }
    static double Na1ac0f056(Object []i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 3;
        } else if (((Double) i[5]).doubleValue() <= 41.275301) {
            p = 3;
        } else if (((Double) i[5]).doubleValue() > 41.275301) {
            p = WekaClassifier2.N57bdb3f957(i);
        }
        return p;
    }
    static double N57bdb3f957(Object []i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 2;
        } else if (((Double) i[1]).doubleValue() <= 236.037766) {
            p = 2;
        } else if (((Double) i[1]).doubleValue() > 236.037766) {
            p = 3;
        }
        return p;
    }
    static double N38052a8558(Object []i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 2;
        } else if (((Double) i[7]).doubleValue() <= 68.209262) {
            p = WekaClassifier2.N25a3155559(i);
        } else if (((Double) i[7]).doubleValue() > 68.209262) {
            p = 1;
        }
        return p;
    }
    static double N25a3155559(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 2;
        } else if (((Double) i[0]).doubleValue() <= 1785.113488) {
            p = WekaClassifier2.N79e25c5160(i);
        } else if (((Double) i[0]).doubleValue() > 1785.113488) {
            p = 3;
        }
        return p;
    }
    static double N79e25c5160(Object []i) {
        double p = Double.NaN;
        if (i[64] == null) {
            p = 3;
        } else if (((Double) i[64]).doubleValue() <= 39.22107) {
            p = 3;
        } else if (((Double) i[64]).doubleValue() > 39.22107) {
            p = WekaClassifier2.N437a1aef61(i);
        }
        return p;
    }
    static double N437a1aef61(Object []i) {
        double p = Double.NaN;
        if (i[11] == null) {
            p = 3;
        } else if (((Double) i[11]).doubleValue() <= 5.426477) {
            p = WekaClassifier2.N52ba410762(i);
        } else if (((Double) i[11]).doubleValue() > 5.426477) {
            p = WekaClassifier2.N6f0eefb863(i);
        }
        return p;
    }
    static double N52ba410762(Object []i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 2;
        } else if (((Double) i[5]).doubleValue() <= 20.597999) {
            p = 2;
        } else if (((Double) i[5]).doubleValue() > 20.597999) {
            p = 3;
        }
        return p;
    }
    static double N6f0eefb863(Object []i) {
        double p = Double.NaN;
        if (i[10] == null) {
            p = 2;
        } else if (((Double) i[10]).doubleValue() <= 24.469353) {
            p = 2;
        } else if (((Double) i[10]).doubleValue() > 24.469353) {
            p = WekaClassifier2.N4e536dc64(i);
        }
        return p;
    }
    static double N4e536dc64(Object []i) {
        double p = Double.NaN;
        if (i[9] == null) {
            p = 3;
        } else if (((Double) i[9]).doubleValue() <= 32.985561) {
            p = 3;
        } else if (((Double) i[9]).doubleValue() > 32.985561) {
            p = 2;
        }
        return p;
    }
    static double N17dbe9cd65(Object []i) {
        double p = Double.NaN;
        if (i[10] == null) {
            p = 2;
        } else if (((Double) i[10]).doubleValue() <= 48.919118) {
            p = WekaClassifier2.N1769204866(i);
        } else if (((Double) i[10]).doubleValue() > 48.919118) {
            p = WekaClassifier2.N736a627379(i);
        }
        return p;
    }
    static double N1769204866(Object []i) {
        double p = Double.NaN;
        if (i[64] == null) {
            p = 3;
        } else if (((Double) i[64]).doubleValue() <= 39.710469) {
            p = WekaClassifier2.Nf027e3267(i);
        } else if (((Double) i[64]).doubleValue() > 39.710469) {
            p = WekaClassifier2.N287be7f971(i);
        }
        return p;
    }
    static double Nf027e3267(Object []i) {
        double p = Double.NaN;
        if (i[20] == null) {
            p = 3;
        } else if (((Double) i[20]).doubleValue() <= 3.07693) {
            p = 3;
        } else if (((Double) i[20]).doubleValue() > 3.07693) {
            p = WekaClassifier2.N22fcfad868(i);
        }
        return p;
    }
    static double N22fcfad868(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 2;
        } else if (((Double) i[0]).doubleValue() <= 1122.77526) {
            p = 2;
        } else if (((Double) i[0]).doubleValue() > 1122.77526) {
            p = WekaClassifier2.N6aa1facb69(i);
        }
        return p;
    }
    static double N6aa1facb69(Object []i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 2;
        } else if (((Double) i[5]).doubleValue() <= 39.082993) {
            p = WekaClassifier2.N3494fcb770(i);
        } else if (((Double) i[5]).doubleValue() > 39.082993) {
            p = 3;
        }
        return p;
    }
    static double N3494fcb770(Object []i) {
        double p = Double.NaN;
        if (i[3] == null) {
            p = 3;
        } else if (((Double) i[3]).doubleValue() <= 74.84745) {
            p = 3;
        } else if (((Double) i[3]).doubleValue() > 74.84745) {
            p = 2;
        }
        return p;
    }
    static double N287be7f971(Object []i) {
        double p = Double.NaN;
        if (i[3] == null) {
            p = 2;
        } else if (((Double) i[3]).doubleValue() <= 287.003031) {
            p = WekaClassifier2.N6d14f21472(i);
        } else if (((Double) i[3]).doubleValue() > 287.003031) {
            p = WekaClassifier2.N1f702b8477(i);
        }
        return p;
    }
    static double N6d14f21472(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 2;
        } else if (((Double) i[0]).doubleValue() <= 1860.144455) {
            p = 2;
        } else if (((Double) i[0]).doubleValue() > 1860.144455) {
            p = WekaClassifier2.N8062adf73(i);
        }
        return p;
    }
    static double N8062adf73(Object []i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 2;
        } else if (((Double) i[5]).doubleValue() <= 75.677291) {
            p = WekaClassifier2.N1be41e2c74(i);
        } else if (((Double) i[5]).doubleValue() > 75.677291) {
            p = 3;
        }
        return p;
    }
    static double N1be41e2c74(Object []i) {
        double p = Double.NaN;
        if (i[16] == null) {
            p = 2;
        } else if (((Double) i[16]).doubleValue() <= 7.468145) {
            p = WekaClassifier2.N6c57668675(i);
        } else if (((Double) i[16]).doubleValue() > 7.468145) {
            p = 2;
        }
        return p;
    }
    static double N6c57668675(Object []i) {
        double p = Double.NaN;
        if (i[6] == null) {
            p = 2;
        } else if (((Double) i[6]).doubleValue() <= 34.975633) {
            p = WekaClassifier2.N2619bd4a76(i);
        } else if (((Double) i[6]).doubleValue() > 34.975633) {
            p = 3;
        }
        return p;
    }
    static double N2619bd4a76(Object []i) {
        double p = Double.NaN;
        if (i[30] == null) {
            p = 3;
        } else if (((Double) i[30]).doubleValue() <= 0.344719) {
            p = 3;
        } else if (((Double) i[30]).doubleValue() > 0.344719) {
            p = 2;
        }
        return p;
    }
    static double N1f702b8477(Object []i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 2;
        } else if (((Double) i[4]).doubleValue() <= 155.388048) {
            p = WekaClassifier2.N24de533878(i);
        } else if (((Double) i[4]).doubleValue() > 155.388048) {
            p = 3;
        }
        return p;
    }
    static double N24de533878(Object []i) {
        double p = Double.NaN;
        if (i[6] == null) {
            p = 3;
        } else if (((Double) i[6]).doubleValue() <= 37.347474) {
            p = 3;
        } else if (((Double) i[6]).doubleValue() > 37.347474) {
            p = 2;
        }
        return p;
    }
    static double N736a627379(Object []i) {
        double p = Double.NaN;
        if (i[8] == null) {
            p = 2;
        } else if (((Double) i[8]).doubleValue() <= 74.244375) {
            p = WekaClassifier2.N18f262b980(i);
        } else if (((Double) i[8]).doubleValue() > 74.244375) {
            p = 1;
        }
        return p;
    }
    static double N18f262b980(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 2;
        } else if (((Double) i[0]).doubleValue() <= 1893.73273) {
            p = 2;
        } else if (((Double) i[0]).doubleValue() > 1893.73273) {
            p = 3;
        }
        return p;
    }
}