package edu.cs65.don.myruns.helpers;

/**
 * Created by don on 5/8/16.
 */

class WekaClassifier3 {

    public static double classify(Object[] i)
            throws Exception {

        double p = Double.NaN;
        p = WekaClassifier3.N50bbf80191(i);
        return p;
    }
    static double N50bbf80191(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 0;
        } else if (((Double) i[0]).doubleValue() <= 89.937769) {
            p = WekaClassifier3.N39b9668192(i);
        } else if (((Double) i[0]).doubleValue() > 89.937769) {
            p = WekaClassifier3.N11b2979595(i);
        }
        return p;
    }
    static double N39b9668192(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 0;
        } else if (((Double) i[0]).doubleValue() <= 61.790954) {
            p = 0;
        } else if (((Double) i[0]).doubleValue() > 61.790954) {
            p = WekaClassifier3.N2d87b36093(i);
        }
        return p;
    }
    static double N2d87b36093(Object []i) {
        double p = Double.NaN;
        if (i[32] == null) {
            p = 0;
        } else if (((Double) i[32]).doubleValue() <= 0.347662) {
            p = WekaClassifier3.N1ec61fbc94(i);
        } else if (((Double) i[32]).doubleValue() > 0.347662) {
            p = 0;
        }
        return p;
    }
    static double N1ec61fbc94(Object []i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 0;
        } else if (((Double) i[7]).doubleValue() <= 0.865902) {
            p = 0;
        } else if (((Double) i[7]).doubleValue() > 0.865902) {
            p = 1;
        }
        return p;
    }
    static double N11b2979595(Object []i) {
        double p = Double.NaN;
        if (i[64] == null) {
            p = 1;
        } else if (((Double) i[64]).doubleValue() <= 10.264833) {
            p = WekaClassifier3.N63cd764c96(i);
        } else if (((Double) i[64]).doubleValue() > 10.264833) {
            p = WekaClassifier3.N5028478c100(i);
        }
        return p;
    }
    static double N63cd764c96(Object []i) {
        double p = Double.NaN;
        if (i[29] == null) {
            p = 1;
        } else if (((Double) i[29]).doubleValue() <= 2.550943) {
            p = WekaClassifier3.N7341c5e697(i);
        } else if (((Double) i[29]).doubleValue() > 2.550943) {
            p = WekaClassifier3.N58df35cd99(i);
        }
        return p;
    }
    static double N7341c5e697(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 1;
        } else if (((Double) i[0]).doubleValue() <= 112.971888) {
            p = WekaClassifier3.N5d540fcd98(i);
        } else if (((Double) i[0]).doubleValue() > 112.971888) {
            p = 1;
        }
        return p;
    }
    static double N5d540fcd98(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 1;
        } else if (((Double) i[0]).doubleValue() <= 109.507205) {
            p = 1;
        } else if (((Double) i[0]).doubleValue() > 109.507205) {
            p = 0;
        }
        return p;
    }
    static double N58df35cd99(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 0;
        } else if (((Double) i[0]).doubleValue() <= 273.344307) {
            p = 0;
        } else if (((Double) i[0]).doubleValue() > 273.344307) {
            p = 1;
        }
        return p;
    }
    static double N5028478c100(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 2;
        } else if (((Double) i[0]).doubleValue() <= 1739.552303) {
            p = 2;
        } else if (((Double) i[0]).doubleValue() > 1739.552303) {
            p = 3;
        }
        return p;
    }
}