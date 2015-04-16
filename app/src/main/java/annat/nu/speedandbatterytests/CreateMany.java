package annat.nu.speedandbatterytests;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import annat.nu.speedandbatterytests.dbflow.DbFlowTable1;
import annat.nu.speedandbatterytests.pure.MyObject;

public class CreateMany {

    public static Collection<MyObject> createMyObject(int count){
        List<MyObject> result = new ArrayList<>(count);
        int version = 0;
        for(int i=0; i<count; i++){
            MyObject myObject = new MyObject();
            myObject.uuid = UUID.randomUUID();
            myObject.version = version++;
            if((UUID.randomUUID().getLeastSignificantBits() & 0xf) < 3){
                version=0;
            }
            myObject.timeStamp = Calendar.getInstance();
            myObject.data = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua";
            result.add(myObject);
        }
        return result;
    }

    public static Collection<DbFlowTable1> createDbFlowTable1(int count){
        List<DbFlowTable1> result = new ArrayList<>(count);
        int version = 0;
        for(int i=0; i<count; i++){
            DbFlowTable1 myObject = new DbFlowTable1();
            myObject.uuid = UUID.randomUUID();
            myObject.version = version++;
            if((UUID.randomUUID().getLeastSignificantBits() & 0xf) < 3){
                version=0;
            }
            myObject.timeStamp = Calendar.getInstance();
            myObject.data = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua";
            result.add(myObject);
        }
        return result;
    }
}
