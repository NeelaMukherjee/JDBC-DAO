package daos;

import Utils.DBType;
import Utils.DBUtil;
import models.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

    public class CarDAO extends DAO<Car> {


        private static final String INSERT = "Insert into Cars.Car_Details" +
                "(Id, Make, Model, Year,Color, Vin)" +
                "values(?,?,?,?,?,?)";

        private static final String GET_ONE = "SELECT * FROM Cars.Car_Details WHERE Id = ?";
        private static final String GET_ALL = "SELECT * FROM Cars.Car_Details";

        private static final String DELETE = "DELETE FROM Cars.Car_Details WHERE Id = ?";
        private static final String UPDATE = "Update Cars.Car_Details set Id = ?, Make = ?, Model = ?, Year = ?, Color =?, Vin=?";

        public CarDAO(Connection conn) {
            super(conn);
        }


        @Override
        public Car findById(int id) {
            Car car = null;
            try (PreparedStatement pstmt = DBUtil.getConnection(DBType.MYSQLDB).prepareStatement(GET_ONE);) {
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    car = new Car();
                    car.setId(rs.getInt("Id"));
                    car.setMake(rs.getString("Make"));
                    car.setModel(rs.getString("Model"));
                    car.setYear(rs.getInt("Year"));
                    car.setColor(rs.getString("Color"));
                    car.setVin(rs.getString("Vin"));
                }
            } catch (
                    SQLException e) {
                DBUtil.showErrorMessage(e);
            }
            return car;
        }

        @Override
        public List<Car> findAll() {
            List<Car> carList = new ArrayList<>();

            Car car =null;
            try(PreparedStatement pstmt = this.connection.prepareStatement(GET_ALL);){
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    car = new Car();
                    car.setId(rs.getInt("Id"));
                    car.setMake(rs.getString("Make"));
                    car.setModel(rs.getString("Model"));
                    car.setYear(rs.getInt("Year"));
                    car.setColor(rs.getString("Color"));
                    car.setVin(rs.getString("Vin"));
                    carList.add(car);
                }
            } catch (
                    SQLException e) {
                DBUtil.showErrorMessage(e);
            }

            for ( int i= 1; i<carList.size(); i++){
                carList.get(i).toString();

            }
            return carList;
        }


        @Override
        public Car update(Car dto) {
            Car car =null;
            try(PreparedStatement pstmt = this.connection.prepareStatement(UPDATE);){
                pstmt.setInt(1, dto.getId());
                pstmt.setString(2, dto.getMake());
                pstmt.setString(3, dto.getModel());
                pstmt.setInt(4, dto.getYear());
                pstmt.setString(5, dto.getColor());
                pstmt.setString(6, dto.getVin());
                car = this.findById(dto.getId());

            }catch (SQLException e){
                DBUtil.showErrorMessage(e);
            }
            return car;
        }

        @Override
        public Car create(Car dto) {

            Car car = null;
            try(PreparedStatement pstmt = this.connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);) {
                pstmt.setInt(1, dto.getId());
                pstmt.setString(2, dto.getMake());
                pstmt.setString(3, dto.getModel());
                pstmt.setInt(4, dto.getYear());
                pstmt.setString(5, dto.getColor());
                pstmt.setString(6, dto.getVin());
                pstmt.executeUpdate();

                ResultSet rs = pstmt.getGeneratedKeys();
                int key = rs.next() ? rs.getInt(1):0;

                if (key!=0){
                    car = findById(key);
                    System.out.println(key);
                }
                //TODO: get newly created auto_generated id from created record and return it below
            } catch (SQLException e) {
                DBUtil.showErrorMessage(e);
            }
            return car;
        }





        @Override
        public void delete(int id) {
            try (PreparedStatement pstmt = this.connection.prepareStatement(DELETE);){
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
            }catch (SQLException e){
                DBUtil.showErrorMessage(e);


            }


        }
    }


