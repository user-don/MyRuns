package edu.cs65.don.myruns.helpers;

import com.google.android.gms.maps.model.LatLng;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import edu.cs65.don.myruns.models.ExerciseEntry;

/**
 * Created by don on 4/24/16.
 */
public class Serializer {

    public static byte[] serialize(String str) throws IOException {
        try(ByteArrayOutputStream b = new ByteArrayOutputStream()){
            try(DataOutputStream o = new DataOutputStream(b)){
                o.writeUTF(obj);
            }
            return b.toByteArray();
        }
    }


    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        try(ByteArrayInputStream b = new ByteArrayInputStream(bytes)){
            try(ObjectInputStream o = new ObjectInputStream(b)){
                return o.readObject();
            }
        }
    }

    public static ArrayList<LatLng> deserializeToArraylist(byte[] bytes)
            throws IOException, ClassNotFoundException {
        try(ByteArrayInputStream b = new ByteArrayInputStream(bytes)){
            try(ObjectInputStream o = new ObjectInputStream(b)){
                return (ArrayList<LatLng>) o.readObject();
            }
        }
    }

}