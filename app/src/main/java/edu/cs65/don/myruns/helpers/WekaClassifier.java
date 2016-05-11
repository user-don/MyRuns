package edu.cs65.don.myruns.helpers;

/**
 * Created by McFarland on 5/9/16.
 */
class WekaClassifier {

    public static double classify(Object[] i)
            throws Exception {

        double p = Double.NaN;
        p = WekaClassifier.N14c8e2170(i);
        return p;
    }
    static double N14c8e2170(Object []i) {
        double p = Double.NaN;
        if (i[64] == null) {
            p = 1;
        } else if (((Double) i[64]).doubleValue() <= 20.328784) {
            p = WekaClassifier.N68f7a5891(i);
        } else if (((Double) i[64]).doubleValue() > 20.328784) {
            p = WekaClassifier.N1e8230d644(i);
        }
        return p;
    }
    static double N68f7a5891(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 0;
        } else if (((Double) i[0]).doubleValue() <= 50.013663) {
            p = 0;
        } else if (((Double) i[0]).doubleValue() > 50.013663) {
            p = WekaClassifier.N37ba94192(i);
        }
        return p;
    }
    static double N37ba94192(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 1;
        } else if (((Double) i[0]).doubleValue() <= 160.341464) {
            p = WekaClassifier.N18127b4e3(i);
        } else if (((Double) i[0]).doubleValue() > 160.341464) {
            p = WekaClassifier.N2cec84a116(i);
        }
        return p;
    }
    static double N18127b4e3(Object []i) {
        double p = Double.NaN;
        if (i[19] == null) {
            p = 0;
        } else if (((Double) i[19]).doubleValue() <= 0.837845) {
            p = WekaClassifier.N4b99fa014(i);
        } else if (((Double) i[19]).doubleValue() > 0.837845) {
            p = WekaClassifier.N3291270f12(i);
        }
        return p;
    }
    static double N4b99fa014(Object []i) {
        double p = Double.NaN;
        if (i[28] == null) {
            p = 2;
        } else if (((Double) i[28]).doubleValue() <= 0.11372) {
            p = 2;
        } else if (((Double) i[28]).doubleValue() > 0.11372) {
            p = WekaClassifier.N1e97c5165(i);
        }
        return p;
    }
    static double N1e97c5165(Object []i) {
        double p = Double.NaN;
        if (i[32] == null) {
            p = 1;
        } else if (((Double) i[32]).doubleValue() <= 0.09002) {
            p = 1;
        } else if (((Double) i[32]).doubleValue() > 0.09002) {
            p = WekaClassifier.N7dee90816(i);
        }
        return p;
    }
    static double N7dee90816(Object []i) {
        double p = Double.NaN;
        if (i[9] == null) {
            p = 0;
        } else if (((Double) i[9]).doubleValue() <= 2.375481) {
            p = WekaClassifier.N76ad4c797(i);
        } else if (((Double) i[9]).doubleValue() > 2.375481) {
            p = 0;
        }
        return p;
    }
    static double N76ad4c797(Object []i) {
        double p = Double.NaN;
        if (i[29] == null) {
            p = 0;
        } else if (((Double) i[29]).doubleValue() <= 0.718355) {
            p = WekaClassifier.N7260f95f8(i);
        } else if (((Double) i[29]).doubleValue() > 0.718355) {
            p = 1;
        }
        return p;
    }
    static double N7260f95f8(Object []i) {
        double p = Double.NaN;
        if (i[22] == null) {
            p = 0;
        } else if (((Double) i[22]).doubleValue() <= 0.665058) {
            p = WekaClassifier.N52ff2d709(i);
        } else if (((Double) i[22]).doubleValue() > 0.665058) {
            p = WekaClassifier.N47ce84a811(i);
        }
        return p;
    }
    static double N52ff2d709(Object []i) {
        double p = Double.NaN;
        if (i[25] == null) {
            p = 0;
        } else if (((Double) i[25]).doubleValue() <= 0.460782) {
            p = 0;
        } else if (((Double) i[25]).doubleValue() > 0.460782) {
            p = WekaClassifier.N5b1cc53610(i);
        }
        return p;
    }
    static double N5b1cc53610(Object []i) {
        double p = Double.NaN;
        if (i[26] == null) {
            p = 1;
        } else if (((Double) i[26]).doubleValue() <= 0.465914) {
            p = 1;
        } else if (((Double) i[26]).doubleValue() > 0.465914) {
            p = 0;
        }
        return p;
    }
    static double N47ce84a811(Object []i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 0;
        } else if (((Double) i[2]).doubleValue() <= 9.519692) {
            p = 0;
        } else if (((Double) i[2]).doubleValue() > 9.519692) {
            p = 3;
        }
        return p;
    }
    static double N3291270f12(Object []i) {
        double p = Double.NaN;
        if (i[10] == null) {
            p = 1;
        } else if (((Double) i[10]).doubleValue() <= 5.509016) {
            p = WekaClassifier.N192e088013(i);
        } else if (((Double) i[10]).doubleValue() > 5.509016) {
            p = 0;
        }
        return p;
    }
    static double N192e088013(Object []i) {
        double p = Double.NaN;
        if (i[15] == null) {
            p = 1;
        } else if (((Double) i[15]).doubleValue() <= 1.348057) {
            p = WekaClassifier.N24a65be614(i);
        } else if (((Double) i[15]).doubleValue() > 1.348057) {
            p = 1;
        }
        return p;
    }
    static double N24a65be614(Object []i) {
        double p = Double.NaN;
        if (i[6] == null) {
            p = 1;
        } else if (((Double) i[6]).doubleValue() <= 4.149472) {
            p = WekaClassifier.N177e701e15(i);
        } else if (((Double) i[6]).doubleValue() > 4.149472) {
            p = 0;
        }
        return p;
    }
    static double N177e701e15(Object []i) {
        double p = Double.NaN;
        if (i[30] == null) {
            p = 1;
        } else if (((Double) i[30]).doubleValue() <= 1.007914) {
            p = 1;
        } else if (((Double) i[30]).doubleValue() > 1.007914) {
            p = 0;
        }
        return p;
    }
    static double N2cec84a116(Object []i) {
        double p = Double.NaN;
        if (i[11] == null) {
            p = 1;
        } else if (((Double) i[11]).doubleValue() <= 13.724877) {
            p = WekaClassifier.N456fe3b417(i);
        } else if (((Double) i[11]).doubleValue() > 13.724877) {
            p = WekaClassifier.N930e81041(i);
        }
        return p;
    }
    static double N456fe3b417(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 1;
        } else if (((Double) i[0]).doubleValue() <= 387.493736) {
            p = WekaClassifier.N5300e99418(i);
        } else if (((Double) i[0]).doubleValue() > 387.493736) {
            p = WekaClassifier.N3ae69ee536(i);
        }
        return p;
    }
    static double N5300e99418(Object []i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 1;
        } else if (((Double) i[4]).doubleValue() <= 24.892678) {
            p = WekaClassifier.N371a8d6a19(i);
        } else if (((Double) i[4]).doubleValue() > 24.892678) {
            p = WekaClassifier.N2fa87ae134(i);
        }
        return p;
    }
    static double N371a8d6a19(Object []i) {
        double p = Double.NaN;
        if (i[24] == null) {
            p = 1;
        } else if (((Double) i[24]).doubleValue() <= 0.848747) {
            p = WekaClassifier.N7f9bcea420(i);
        } else if (((Double) i[24]).doubleValue() > 0.848747) {
            p = WekaClassifier.N11cbedd722(i);
        }
        return p;
    }
    static double N7f9bcea420(Object []i) {
        double p = Double.NaN;
        if (i[24] == null) {
            p = 1;
        } else if (((Double) i[24]).doubleValue() <= 0.262925) {
            p = WekaClassifier.N4553f0a021(i);
        } else if (((Double) i[24]).doubleValue() > 0.262925) {
            p = 1;
        }
        return p;
    }
    static double N4553f0a021(Object []i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 1;
        } else if (((Double) i[1]).doubleValue() <= 46.570062) {
            p = 1;
        } else if (((Double) i[1]).doubleValue() > 46.570062) {
            p = 2;
        }
        return p;
    }
    static double N11cbedd722(Object []i) {
        double p = Double.NaN;
        if (i[21] == null) {
            p = 0;
        } else if (((Double) i[21]).doubleValue() <= 0.386722) {
            p = 0;
        } else if (((Double) i[21]).doubleValue() > 0.386722) {
            p = WekaClassifier.N7259435923(i);
        }
        return p;
    }
    static double N7259435923(Object []i) {
        double p = Double.NaN;
        if (i[6] == null) {
            p = 2;
        } else if (((Double) i[6]).doubleValue() <= 2.650753) {
            p = WekaClassifier.N53165d5824(i);
        } else if (((Double) i[6]).doubleValue() > 2.650753) {
            p = WekaClassifier.N99a716a25(i);
        }
        return p;
    }
    static double N53165d5824(Object []i) {
        double p = Double.NaN;
        if (i[11] == null) {
            p = 2;
        } else if (((Double) i[11]).doubleValue() <= 4.853133) {
            p = 2;
        } else if (((Double) i[11]).doubleValue() > 4.853133) {
            p = 1;
        }
        return p;
    }
    static double N99a716a25(Object []i) {
        double p = Double.NaN;
        if (i[22] == null) {
            p = 1;
        } else if (((Double) i[22]).doubleValue() <= 7.938165) {
            p = WekaClassifier.N477180db26(i);
        } else if (((Double) i[22]).doubleValue() > 7.938165) {
            p = 2;
        }
        return p;
    }
    static double N477180db26(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 1;
        } else if (((Double) i[0]).doubleValue() <= 267.809562) {
            p = WekaClassifier.N7f0a40e727(i);
        } else if (((Double) i[0]).doubleValue() > 267.809562) {
            p = WekaClassifier.Nf602a5230(i);
        }
        return p;
    }
    static double N7f0a40e727(Object []i) {
        double p = Double.NaN;
        if (i[10] == null) {
            p = 1;
        } else if (((Double) i[10]).doubleValue() <= 4.60243) {
            p = 1;
        } else if (((Double) i[10]).doubleValue() > 4.60243) {
            p = WekaClassifier.N1e172ec328(i);
        }
        return p;
    }
    static double N1e172ec328(Object []i) {
        double p = Double.NaN;
        if (i[13] == null) {
            p = 2;
        } else if (((Double) i[13]).doubleValue() <= 3.302954) {
            p = WekaClassifier.N44dc0d4a29(i);
        } else if (((Double) i[13]).doubleValue() > 3.302954) {
            p = 1;
        }
        return p;
    }
    static double N44dc0d4a29(Object []i) {
        double p = Double.NaN;
        if (i[21] == null) {
            p = 2;
        } else if (((Double) i[21]).doubleValue() <= 3.100665) {
            p = 2;
        } else if (((Double) i[21]).doubleValue() > 3.100665) {
            p = 0;
        }
        return p;
    }
    static double Nf602a5230(Object []i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 2;
        } else if (((Double) i[5]).doubleValue() <= 12.942888) {
            p = WekaClassifier.N22404e2131(i);
        } else if (((Double) i[5]).doubleValue() > 12.942888) {
            p = WekaClassifier.N5060d9c32(i);
        }
        return p;
    }
    static double N22404e2131(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 3;
        } else if (((Double) i[0]).doubleValue() <= 325.489173) {
            p = 3;
        } else if (((Double) i[0]).doubleValue() > 325.489173) {
            p = 2;
        }
        return p;
    }
    static double N5060d9c32(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 1;
        } else if (((Double) i[0]).doubleValue() <= 294.274849) {
            p = WekaClassifier.N2114f62b33(i);
        } else if (((Double) i[0]).doubleValue() > 294.274849) {
            p = 1;
        }
        return p;
    }
    static double N2114f62b33(Object []i) {
        double p = Double.NaN;
        if (i[3] == null) {
            p = 2;
        } else if (((Double) i[3]).doubleValue() <= 47.022993) {
            p = 2;
        } else if (((Double) i[3]).doubleValue() > 47.022993) {
            p = 1;
        }
        return p;
    }
    static double N2fa87ae134(Object []i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 1;
        } else if (((Double) i[2]).doubleValue() <= 11.620845) {
            p = WekaClassifier.Nf39187335(i);
        } else if (((Double) i[2]).doubleValue() > 11.620845) {
            p = 1;
        }
        return p;
    }
    static double Nf39187335(Object []i) {
        double p = Double.NaN;
        if (i[3] == null) {
            p = 0;
        } else if (((Double) i[3]).doubleValue() <= 38.952357) {
            p = 0;
        } else if (((Double) i[3]).doubleValue() > 38.952357) {
            p = 1;
        }
        return p;
    }
    static double N3ae69ee536(Object []i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 2;
        } else if (((Double) i[2]).doubleValue() <= 30.922752) {
            p = 2;
        } else if (((Double) i[2]).doubleValue() > 30.922752) {
            p = WekaClassifier.N3cc5823c37(i);
        }
        return p;
    }
    static double N3cc5823c37(Object []i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 2;
        } else if (((Double) i[1]).doubleValue() <= 51.654862) {
            p = WekaClassifier.N5056883738(i);
        } else if (((Double) i[1]).doubleValue() > 51.654862) {
            p = WekaClassifier.N176be8ce39(i);
        }
        return p;
    }
    static double N5056883738(Object []i) {
        double p = Double.NaN;
        if (i[3] == null) {
            p = 2;
        } else if (((Double) i[3]).doubleValue() <= 46.290294) {
            p = 2;
        } else if (((Double) i[3]).doubleValue() > 46.290294) {
            p = 1;
        }
        return p;
    }
    static double N176be8ce39(Object []i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 1;
        } else if (((Double) i[1]).doubleValue() <= 107.586119) {
            p = WekaClassifier.N19f44d7040(i);
        } else if (((Double) i[1]).doubleValue() > 107.586119) {
            p = 3;
        }
        return p;
    }
    static double N19f44d7040(Object []i) {
        double p = Double.NaN;
        if (i[3] == null) {
            p = 0;
        } else if (((Double) i[3]).doubleValue() <= 25.513276) {
            p = 0;
        } else if (((Double) i[3]).doubleValue() > 25.513276) {
            p = 1;
        }
        return p;
    }
    static double N930e81041(Object []i) {
        double p = Double.NaN;
        if (i[25] == null) {
            p = 2;
        } else if (((Double) i[25]).doubleValue() <= 5.939468) {
            p = WekaClassifier.N6d43de3142(i);
        } else if (((Double) i[25]).doubleValue() > 5.939468) {
            p = WekaClassifier.N252676cf43(i);
        }
        return p;
    }
    static double N6d43de3142(Object []i) {
        double p = Double.NaN;
        if (i[8] == null) {
            p = 0;
        } else if (((Double) i[8]).doubleValue() <= 5.759165) {
            p = 0;
        } else if (((Double) i[8]).doubleValue() > 5.759165) {
            p = 2;
        }
        return p;
    }
    static double N252676cf43(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 1;
        } else if (((Double) i[0]).doubleValue() <= 325.489173) {
            p = 1;
        } else if (((Double) i[0]).doubleValue() > 325.489173) {
            p = 3;
        }
        return p;
    }
    static double N1e8230d644(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 2;
        } else if (((Double) i[0]).doubleValue() <= 1535.531126) {
            p = WekaClassifier.N10fc3b7e45(i);
        } else if (((Double) i[0]).doubleValue() > 1535.531126) {
            p = WekaClassifier.N7dfe3c1a57(i);
        }
        return p;
    }
    static double N10fc3b7e45(Object []i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 2;
        } else if (((Double) i[1]).doubleValue() <= 104.684061) {
            p = WekaClassifier.N63bdcd0646(i);
        } else if (((Double) i[1]).doubleValue() > 104.684061) {
            p = WekaClassifier.N63e0f3550(i);
        }
        return p;
    }
    static double N63bdcd0646(Object []i) {
        double p = Double.NaN;
        if (i[3] == null) {
            p = 2;
        } else if (((Double) i[3]).doubleValue() <= 191.466446) {
            p = WekaClassifier.N75bb5fb047(i);
        } else if (((Double) i[3]).doubleValue() > 191.466446) {
            p = 3;
        }
        return p;
    }
    static double N75bb5fb047(Object []i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 3;
        } else if (((Double) i[7]).doubleValue() <= 21.381197) {
            p = 3;
        } else if (((Double) i[7]).doubleValue() > 21.381197) {
            p = WekaClassifier.N7a30c55148(i);
        }
        return p;
    }
    static double N7a30c55148(Object []i) {
        double p = Double.NaN;
        if (i[19] == null) {
            p = 2;
        } else if (((Double) i[19]).doubleValue() <= 13.436974) {
            p = WekaClassifier.Nb6626a249(i);
        } else if (((Double) i[19]).doubleValue() > 13.436974) {
            p = 2;
        }
        return p;
    }
    static double Nb6626a249(Object []i) {
        double p = Double.NaN;
        if (i[11] == null) {
            p = 2;
        } else if (((Double) i[11]).doubleValue() <= 19.484276) {
            p = 2;
        } else if (((Double) i[11]).doubleValue() > 19.484276) {
            p = 3;
        }
        return p;
    }
    static double N63e0f3550(Object []i) {
        double p = Double.NaN;
        if (i[11] == null) {
            p = 2;
        } else if (((Double) i[11]).doubleValue() <= 9.722562) {
            p = WekaClassifier.N2957869d51(i);
        } else if (((Double) i[11]).doubleValue() > 9.722562) {
            p = WekaClassifier.N19e946e054(i);
        }
        return p;
    }
    static double N2957869d51(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 2;
        } else if (((Double) i[0]).doubleValue() <= 1300.923372) {
            p = WekaClassifier.N55b1c4ab52(i);
        } else if (((Double) i[0]).doubleValue() > 1300.923372) {
            p = 3;
        }
        return p;
    }
    static double N55b1c4ab52(Object []i) {
        double p = Double.NaN;
        if (i[8] == null) {
            p = 2;
        } else if (((Double) i[8]).doubleValue() <= 6.412976) {
            p = WekaClassifier.N6fd8ab8953(i);
        } else if (((Double) i[8]).doubleValue() > 6.412976) {
            p = 2;
        }
        return p;
    }
    static double N6fd8ab8953(Object []i) {
        double p = Double.NaN;
        if (i[8] == null) {
            p = 2;
        } else if (((Double) i[8]).doubleValue() <= 3.509445) {
            p = 2;
        } else if (((Double) i[8]).doubleValue() > 3.509445) {
            p = 3;
        }
        return p;
    }
    static double N19e946e054(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 2;
        } else if (((Double) i[0]).doubleValue() <= 1264.379326) {
            p = 2;
        } else if (((Double) i[0]).doubleValue() > 1264.379326) {
            p = WekaClassifier.Nc9f482e55(i);
        }
        return p;
    }
    static double Nc9f482e55(Object []i) {
        double p = Double.NaN;
        if (i[15] == null) {
            p = 2;
        } else if (((Double) i[15]).doubleValue() <= 41.423205) {
            p = WekaClassifier.N57d0418a56(i);
        } else if (((Double) i[15]).doubleValue() > 41.423205) {
            p = 2;
        }
        return p;
    }
    static double N57d0418a56(Object []i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 2;
        } else if (((Double) i[4]).doubleValue() <= 67.183787) {
            p = 2;
        } else if (((Double) i[4]).doubleValue() > 67.183787) {
            p = 3;
        }
        return p;
    }
    static double N7dfe3c1a57(Object []i) {
        double p = Double.NaN;
        if (i[16] == null) {
            p = 3;
        } else if (((Double) i[16]).doubleValue() <= 78.106947) {
            p = 3;
        } else if (((Double) i[16]).doubleValue() > 78.106947) {
            p = 2;
        }
        return p;
    }
}
