package models;

import Utils.DBType;
import Utils.DBUtil;
import daos.CarDAO;


import java.sql.Connection;
import java.sql.SQLException;


public class Demo {


    public static void main(String... args) {
        try (Connection conn = DBUtil.getConnection(DBType.MYSQLDB);) {
            CarDAO carDAO = new CarDAO(conn);


            Car carCreate = new Car();
            //carCreate.setId(108);
            carCreate.setMake("Honda");
            carCreate.setModel("Civic");
            carCreate.setYear(2019);
            carCreate.setColor("Red");
            carCreate.setVin("54897235VV");

            carDAO.create(carCreate);
            System.out.println(carCreate);


        } catch (SQLException e) {
            DBUtil.showErrorMessage(e);

        }


    }
}

