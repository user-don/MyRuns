package edu.cs65.don.myruns.helpers;

/**
 * Created by don on 5/8/16.
 */
class WekaClassifier4 {

    public static double classify(Object[] i)
            throws Exception {

        double p = Double.NaN;
        p = WekaClassifier4.N666248580(i);
        return p;
    }
    static double N666248580(Object []i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 0;
        } else if (((Double) i[2]).doubleValue() <= 53.483399) {
            p = WekaClassifier4.N4a01dc3b1(i);
        } else if (((Double) i[2]).doubleValue() > 53.483399) {
            p = WekaClassifier4.N6888ceb311(i);
        }
        return p;
    }
    static double N4a01dc3b1(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 0;
        } else if (((Double) i[0]).doubleValue() <= 85.647004) {
            p = WekaClassifier4.N393659852(i);
        } else if (((Double) i[0]).doubleValue() > 85.647004) {
            p = WekaClassifier4.N3aa666a39(i);
        }
        return p;
    }
    static double N393659852(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 0;
        } else if (((Double) i[0]).doubleValue() <= 52.40846) {
            p = WekaClassifier4.N387c4aec3(i);
        } else if (((Double) i[0]).doubleValue() > 52.40846) {
            p = WekaClassifier4.N590c93d57(i);
        }
        return p;
    }
    static double N387c4aec3(Object []i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 0;
        } else if (((Double) i[4]).doubleValue() <= 0.529894) {
            p = WekaClassifier4.N4ca8f5334(i);
        } else if (((Double) i[4]).doubleValue() > 0.529894) {
            p = WekaClassifier4.N88c18995(i);
        }
        return p;
    }
    static double N4ca8f5334(Object []i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 3;
        } else if (((Double) i[2]).doubleValue() <= 1.231367) {
            p = 3;
        } else if (((Double) i[2]).doubleValue() > 1.231367) {
            p = 0;
        }
        return p;
    }
    static double N88c18995(Object []i) {
        double p = Double.NaN;
        if (i[10] == null) {
            p = 0;
        } else if (((Double) i[10]).doubleValue() <= 1.532844) {
            p = 0;
        } else if (((Double) i[10]).doubleValue() > 1.532844) {
            p = WekaClassifier4.N37d827c6(i);
        }
        return p;
    }
    static double N37d827c6(Object []i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 3;
        } else if (((Double) i[1]).doubleValue() <= 10.252635) {
            p = 3;
        } else if (((Double) i[1]).doubleValue() > 10.252635) {
            p = 0;
        }
        return p;
    }
    static double N590c93d57(Object []i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 0;
        } else if (((Double) i[1]).doubleValue() <= 18.996521) {
            p = WekaClassifier4.N5206e4aa8(i);
        } else if (((Double) i[1]).doubleValue() > 18.996521) {
            p = 1;
        }
        return p;
    }
    static double N5206e4aa8(Object []i) {
        double p = Double.NaN;
        if (i[14] == null) {
            p = 1;
        } else if (((Double) i[14]).doubleValue() <= 0.098206) {
            p = 1;
        } else if (((Double) i[14]).doubleValue() > 0.098206) {
            p = 0;
        }
        return p;
    }
    static double N3aa666a39(Object []i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 1;
        } else if (((Double) i[7]).doubleValue() <= 7.970572) {
            p = 1;
        } else if (((Double) i[7]).doubleValue() > 7.970572) {
            p = WekaClassifier4.N55a76de910(i);
        }
        return p;
    }
    static double N55a76de910(Object []i) {
        double p = Double.NaN;
        if (i[29] == null) {
            p = 0;
        } else if (((Double) i[29]).doubleValue() <= 1.1803) {
            p = 0;
        } else if (((Double) i[29]).doubleValue() > 1.1803) {
            p = 1;
        }
        return p;
    }
    static double N6888ceb311(Object []i) {
        double p = Double.NaN;
        if (i[64] == null) {
            p = 2;
        } else if (((Double) i[64]).doubleValue() <= 50.381143) {
            p = 2;
        } else if (((Double) i[64]).doubleValue() > 50.381143) {
            p = 3;
        }
        return p;
    }
}
