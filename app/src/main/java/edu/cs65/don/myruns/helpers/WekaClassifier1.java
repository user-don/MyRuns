package edu.cs65.don.myruns.helpers;

class WekaClassifier1 {

    public static double classify(Object[] i)
            throws Exception {

        double p = Double.NaN;
        p = WekaClassifier1.N4d706f720(i);
        return p;
    }
    static double N4d706f720(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 1;
        } else if (((Double) i[0]).doubleValue() <= 827.084579) {
            p = WekaClassifier1.N55d16f251(i);
        } else if (((Double) i[0]).doubleValue() > 827.084579) {
            p = WekaClassifier1.N1f3660c321(i);
        }
        return p;
    }
    static double N55d16f251(Object []i) {
        double p = Double.NaN;
        if (i[64] == null) {
            p = 1;
        } else if (((Double) i[64]).doubleValue() <= 4.185164) {
            p = WekaClassifier1.N20351ef82(i);
        } else if (((Double) i[64]).doubleValue() > 4.185164) {
            p = WekaClassifier1.N702df97715(i);
        }
        return p;
    }
    static double N20351ef82(Object []i) {
        double p = Double.NaN;
        if (i[21] == null) {
            p = 0;
        } else if (((Double) i[21]).doubleValue() <= 0.142445) {
            p = WekaClassifier1.N59466af43(i);
        } else if (((Double) i[21]).doubleValue() > 0.142445) {
            p = WekaClassifier1.N178395514(i);
        }
        return p;
    }
    static double N59466af43(Object []i) {
        double p = Double.NaN;
        if (i[13] == null) {
            p = 0;
        } else if (((Double) i[13]).doubleValue() <= 0.830892) {
            p = 0;
        } else if (((Double) i[13]).doubleValue() > 0.830892) {
            p = 1;
        }
        return p;
    }
    static double N178395514(Object []i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 1;
        } else if (((Double) i[2]).doubleValue() <= 9.525907) {
            p = WekaClassifier1.N49f568c35(i);
        } else if (((Double) i[2]).doubleValue() > 9.525907) {
            p = WekaClassifier1.N76e0a70611(i);
        }
        return p;
    }
    static double N49f568c35(Object []i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 1;
        } else if (((Double) i[7]).doubleValue() <= 1.073755) {
            p = WekaClassifier1.N413f7ddb6(i);
        } else if (((Double) i[7]).doubleValue() > 1.073755) {
            p = WekaClassifier1.N2b44aec27(i);
        }
        return p;
    }
    static double N413f7ddb6(Object []i) {
        double p = Double.NaN;
        if (i[28] == null) {
            p = 0;
        } else if (((Double) i[28]).doubleValue() <= 0.101307) {
            p = 0;
        } else if (((Double) i[28]).doubleValue() > 0.101307) {
            p = 1;
        }
        return p;
    }
    static double N2b44aec27(Object []i) {
        double p = Double.NaN;
        if (i[23] == null) {
            p = 1;
        } else if (((Double) i[23]).doubleValue() <= 0.44871) {
            p = 1;
        } else if (((Double) i[23]).doubleValue() > 0.44871) {
            p = WekaClassifier1.N20e69fe68(i);
        }
        return p;
    }
    static double N20e69fe68(Object []i) {
        double p = Double.NaN;
        if (i[3] == null) {
            p = 1;
        } else if (((Double) i[3]).doubleValue() <= 20.030925) {
            p = WekaClassifier1.N42b748499(i);
        } else if (((Double) i[3]).doubleValue() > 20.030925) {
            p = 0;
        }
        return p;
    }
    static double N42b748499(Object []i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 1;
        } else if (((Double) i[1]).doubleValue() <= 5.58695) {
            p = WekaClassifier1.N78dc346110(i);
        } else if (((Double) i[1]).doubleValue() > 5.58695) {
            p = 1;
        }
        return p;
    }
    static double N78dc346110(Object []i) {
        double p = Double.NaN;
        if (i[26] == null) {
            p = 1;
        } else if (((Double) i[26]).doubleValue() <= 0.595067) {
            p = 1;
        } else if (((Double) i[26]).doubleValue() > 0.595067) {
            p = 0;
        }
        return p;
    }
    static double N76e0a70611(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 0;
        } else if (((Double) i[0]).doubleValue() <= 62.314215) {
            p = WekaClassifier1.N626d0f9312(i);
        } else if (((Double) i[0]).doubleValue() > 62.314215) {
            p = 1;
        }
        return p;
    }
    static double N626d0f9312(Object []i) {
        double p = Double.NaN;
        if (i[11] == null) {
            p = 0;
        } else if (((Double) i[11]).doubleValue() <= 1.916696) {
            p = WekaClassifier1.N1a60f69713(i);
        } else if (((Double) i[11]).doubleValue() > 1.916696) {
            p = 1;
        }
        return p;
    }
    static double N1a60f69713(Object []i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 0;
        } else if (((Double) i[5]).doubleValue() <= 0.869259) {
            p = WekaClassifier1.N306a68614(i);
        } else if (((Double) i[5]).doubleValue() > 0.869259) {
            p = 0;
        }
        return p;
    }
    static double N306a68614(Object []i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 0;
        } else if (((Double) i[1]).doubleValue() <= 6.73492) {
            p = 0;
        } else if (((Double) i[1]).doubleValue() > 6.73492) {
            p = 1;
        }
        return p;
    }
    static double N702df97715(Object []i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 1;
        } else if (((Double) i[1]).doubleValue() <= 236.387344) {
            p = WekaClassifier1.N7a36717616(i);
        } else if (((Double) i[1]).doubleValue() > 236.387344) {
            p = WekaClassifier1.N776846820(i);
        }
        return p;
    }
    static double N7a36717616(Object []i) {
        double p = Double.NaN;
        if (i[8] == null) {
            p = 1;
        } else if (((Double) i[8]).doubleValue() <= 11.973626) {
            p = WekaClassifier1.N677fe4e917(i);
        } else if (((Double) i[8]).doubleValue() > 11.973626) {
            p = 1;
        }
        return p;
    }
    static double N677fe4e917(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 1;
        } else if (((Double) i[0]).doubleValue() <= 636.076471) {
            p = 1;
        } else if (((Double) i[0]).doubleValue() > 636.076471) {
            p = WekaClassifier1.N51fb5c1618(i);
        }
        return p;
    }
    static double N51fb5c1618(Object []i) {
        double p = Double.NaN;
        if (i[6] == null) {
            p = 2;
        } else if (((Double) i[6]).doubleValue() <= 25.776826) {
            p = WekaClassifier1.N4e02f11f19(i);
        } else if (((Double) i[6]).doubleValue() > 25.776826) {
            p = 1;
        }
        return p;
    }
    static double N4e02f11f19(Object []i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 1;
        } else if (((Double) i[1]).doubleValue() <= 69.022511) {
            p = 1;
        } else if (((Double) i[1]).doubleValue() > 69.022511) {
            p = 2;
        }
        return p;
    }
    static double N776846820(Object []i) {
        double p = Double.NaN;
        if (i[12] == null) {
            p = 2;
        } else if (((Double) i[12]).doubleValue() <= 14.910917) {
            p = 2;
        } else if (((Double) i[12]).doubleValue() > 14.910917) {
            p = 1;
        }
        return p;
    }
    static double N1f3660c321(Object []i) {
        double p = Double.NaN;
        if (i[6] == null) {
            p = 2;
        } else if (((Double) i[6]).doubleValue() <= 69.041595) {
            p = 2;
        } else if (((Double) i[6]).doubleValue() > 69.041595) {
            p = WekaClassifier1.N14a684df22(i);
        }
        return p;
    }
    static double N14a684df22(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 1;
        } else if (((Double) i[0]).doubleValue() <= 946.807793) {
            p = 1;
        } else if (((Double) i[0]).doubleValue() > 946.807793) {
            p = 2;
        }
        return p;
    }
}
