package com.bridgelabz.advaddressbook.db;

import com.bridgelabz.advaddressbook.enums.EditType;

public class BussLayer {
        private final DbManager db;
        public BussLayer(){
            db = new DbManager();
        }
        public void selectData(){
            db.selectRecords();
        }
        public void insertData(String name, Long mobile, String city, String state, int zip){
            String cmd = "insert into person(name,mobile,city,state,zip)" +
                    " values('"+name+"','"+mobile+"','"+city+"','"+state+"','"+zip+"')";
            if(db.recordManipulation(cmd)){
                System.out.println("Record Has Been Saved Sucessfully");
            }else {
                System.out.println("Not Added Successfully.");
            }
        }

        public void insertData(String name,EditType editType, Long number){
            String cmd = null;
            switch(editType){
                case EDIT_NUMBER:
                    cmd = "update person set mobile='"+number+"' where name='"+name+"'";
                    break;
                case EDIT_ZIP:
                    cmd = "update person set zip='"+number+"' where name='"+name+"'";
                    break;
            }
            if(db.recordManipulation(cmd)){
                System.out.println("Updated");
            }else {
                System.out.println("Not Updated");
            }
        }

        public void insertData(String name,EditType editType, String changeInTo){
            String cmd = null;
            switch(editType){
                case EDIT_CITY:
                    cmd = "update person set city='"+changeInTo+"' where name='"+name+"'";
                    break;
                case EDIT_STATE:
                    cmd = "update person set state='"+changeInTo+"' where name='"+name+"'";
                    break;
            }

            if(db.recordManipulation(cmd)){
                System.out.println("Updated");
            }else {
                System.out.println("Not Updated");
            }
        }
        public void deleteData(String name){
            String cmd = "delete from person where name='"+name+"'";
            if(db.recordManipulation(cmd)){
                System.out.println("Deleted");
            }else {
                System.out.println("Not Deleted");
            }
        }

}
