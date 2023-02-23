package Services;

import Entities.Etat_reclamation;
import Entities.Reclamation;
import Utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ServiceReclamation implements IService<Reclamation> {
        Connection cnx;
        Statement stm;

        public ServiceReclamation() {
            cnx = MyDB.getInstance().getCon();

        }
        @Override
        public void ajouter(Reclamation R) {
            java.sql.Date datec = new java.sql.Date(System.currentTimeMillis());
            try {
                String qry = "INSERT INTO `reclamation`( `titre_reclamation`, `description_reclamation`, `etat_reclamation`, `date_creation`,  `id_user`, `archived`) VALUES ('" + R.getTitre_reclamation() + "','" + R.getDescription() + "','" + Etat_reclamation.En_cours + "','" + datec + "','"+R.getId_user() +"','"+ 0 +"')";

                stm =  cnx.createStatement();
                stm.executeUpdate(qry);
            } catch (SQLException ex) {

                System.out.println(ex.getMessage());

            }

        }

      @Override
        public  List<Reclamation> afficher() {
            List<Reclamation> Reclamations = new ArrayList();
            try {

                String qry = "SELECT * FROM `reclamation`  WHERE archived = 0";
                stm = cnx.createStatement();

                ResultSet rs = stm.executeQuery(qry);

                while (rs.next()) {
                    Reclamation p = new Reclamation();
                    p.setId_reclamation(rs.getInt("id_reclamation"));
                    p.setTitre_reclamation(rs.getString("titre_reclamation"));
                    p.setDescription(rs.getString("description_reclamation"));
                    p.setEtat_reclamation(Etat_reclamation.valueOf(rs.getString("etat_reclamation")));
                    p.setDate_creation(rs.getDate("date_creation"));
                    p.setDate_cloture(rs.getDate("date_cloture"));
                    p.setId_reclamation(rs.getInt("id_reclamation"));
                    Reclamations.add(p);
                }



            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

            return Reclamations;

        }
    @Override
    public Boolean modifier(Reclamation R){
        try {
            String qry = "UPDATE `reclamation` SET `titre_reclamation`='"+ R.getTitre_reclamation() +"',`description_reclamation`='" + R.getDescription() + "',`etat_reclamation`='" + R.getEtat_reclamation() + "',`date_creation`='" + R.getDate_creation() + "',`date_cloture`='" + R.getDate_cloture() + "',`id_user`='"+R.getId_user() +"'";

            stm = cnx.createStatement();
            stm.executeUpdate(qry);
            return true ;
        } catch (SQLException ex) {

            System.out.println(ex.getMessage());
            return false ;
        }

    }

    public Boolean supprimer(Reclamation R){

        String qry = "UPDATE `reclamation` SET `archived`='"+ 1 +"' where id_reclamation ='" + R.getId_reclamation() + "'";
        try {
            stm = cnx.createStatement();
            stm.executeUpdate(qry);
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    }



