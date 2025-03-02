package modele.dao;

import java.time.*;
import java.sql.*;
import java.util.*;

import modele.dto.*;
import modele.utils.*;

public class FavorisDAO {
    DS ds = new DataIUT();

    public List<Favori> selectAll() {
        List<Favori> favoris = new ArrayList<>();
        try (Connection con = ds.getConnection()) {
            String requete = "SELECT * FROM Favoris";
            try (Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery(requete)) {
                while (rs.next()) {
                    int uid = rs.getInt("uid");
                    int pid = rs.getInt("pid");
                    LocalDateTime dfavori = BAO.conversion(rs.getTimestamp("dfavori"));
                    favoris.add(new Favori(uid, pid, dfavori));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return favoris;
    }

    public void insert(Favori favori) {
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "INSERT INTO Favoris (uid, pid, dfavori) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, favori.getUid());
                pstmt.setInt(2, favori.getPid());
                pstmt.setTimestamp(3, BAO.conversion(favori.getDfavori()));
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(Favori favori) {
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "DELETE FROM Favoris WHERE uid = ? AND pid = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, favori.getUid());
                pstmt.setInt(2, favori.getPid());
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}