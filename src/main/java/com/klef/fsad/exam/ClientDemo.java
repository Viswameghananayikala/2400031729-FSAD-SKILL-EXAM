package com.klef.fsad.exam;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.List;

/**
 * ClientDemo
 * Demonstrates Hibernate HQL operations on the Service entity:
 *   I.  Insert records using persistent objects.
 *   II. Update Name and Status fields by ID using HQL named parameters.
 *
 * Package : com.klef.fsad.exam
 * Database: fsadexam
 */
public class ClientDemo {

    // ──────────────────────────────────────────────────────────────
    //  I.  INSERT RECORDS USING PERSISTENT OBJECTS
    // ──────────────────────────────────────────────────────────────

    /**
     * Inserts multiple Service records into the database
     * using Hibernate's session.save() (persistent object approach).
     */
    public static void insertRecords(SessionFactory sf) {
        System.out.println("\n========== I. INSERT RECORDS ==========");

        Session session = sf.openSession();
        Transaction tx  = null;

        try {
            tx = session.beginTransaction();

            // --- Record 1 ---
            Service s1 = new Service(
                "Network Setup",
                LocalDate.of(2024, 1, 15),
                "Active",
                "IT Infrastructure",
                "Configure LAN and WAN for office premises",
                15000.00,
                "TechCorp Solutions"
            );
            session.save(s1);   // make s1 a PERSISTENT object
            System.out.println("Inserted → " + s1);

            // --- Record 2 ---
            Service s2 = new Service(
                "Annual Maintenance",
                LocalDate.of(2024, 2, 20),
                "Pending",
                "Maintenance",
                "Yearly hardware maintenance contract",
                8500.00,
                "QuickFix Services"
            );
            session.save(s2);
            System.out.println("Inserted → " + s2);

            // --- Record 3 ---
            Service s3 = new Service(
                "Cloud Migration",
                LocalDate.of(2024, 3, 10),
                "Inactive",
                "Cloud Services",
                "Migrate on-premise servers to AWS",
                50000.00,
                "CloudBase Ltd"
            );
            session.save(s3);
            System.out.println("Inserted → " + s3);

            // --- Record 4 ---
            Service s4 = new Service(
                "Security Audit",
                LocalDate.of(2024, 4, 5),
                "Active",
                "Consultation",
                "Full vulnerability and penetration testing",
                12000.00,
                "SecureNet India"
            );
            session.save(s4);
            System.out.println("Inserted → " + s4);

            // --- Record 5 ---
            Service s5 = new Service(
                "Database Backup",
                LocalDate.of(2024, 5, 1),
                "Active",
                "Data Management",
                "Automated daily backup configuration",
                5000.00,
                "DataSafe Corp"
            );
            session.save(s5);
            System.out.println("Inserted → " + s5);

            tx.commit();
            System.out.println("\n✔ All 5 records inserted successfully.");

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("Insert failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    // ──────────────────────────────────────────────────────────────
    //  II. UPDATE Name AND Status BY ID  (HQL + Named Parameters)
    // ──────────────────────────────────────────────────────────────

    /**
     * Updates the Name and Status of a Service record
     * identified by its ID, using HQL with named parameters.
     *
     * @param sf       the SessionFactory
     * @param id       the ID of the record to update
     * @param newName  the new service name
     * @param newStatus the new service status
     */
    public static void updateNameAndStatus(SessionFactory sf,
                                           int id,
                                           String newName,
                                           String newStatus) {
        System.out.println("\n========== II. UPDATE (HQL Named Parameters) ==========");
        System.out.printf("Updating Service ID=%d → name='%s', status='%s'%n",
                           id, newName, newStatus);

        Session session = sf.openSession();
        Transaction tx  = null;

        try {
            tx = session.beginTransaction();

            // HQL UPDATE with named parameters :newName, :newStatus, :sid
            String hql = "UPDATE Service s " +
                         "SET s.name = :newName, s.status = :newStatus " +
                         "WHERE s.id = :sid";

            Query<?> query = session.createQuery(hql);
            query.setParameter("newName",   newName);
            query.setParameter("newStatus", newStatus);
            query.setParameter("sid",       id);

            int rowsAffected = query.executeUpdate();
            tx.commit();

            if (rowsAffected > 0) {
                System.out.println("✔ Update successful. Rows affected: " + rowsAffected);
            } else {
                System.out.println("⚠ No record found with ID = " + id);
            }

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("Update failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    // ──────────────────────────────────────────────────────────────
    //  HELPER – Display all records using HQL SELECT
    // ──────────────────────────────────────────────────────────────

    /**
     * Fetches and prints all Service records using an HQL SELECT query.
     */
    public static void displayAllServices(SessionFactory sf) {
        System.out.println("\n========== ALL SERVICE RECORDS ==========");

        Session session = sf.openSession();
        try {
            String hql = "FROM Service s ORDER BY s.id";
            Query<Service> query = session.createQuery(hql, Service.class);
            List<Service> services = query.getResultList();

            if (services.isEmpty()) {
                System.out.println("No records found.");
            } else {
                System.out.printf("%-5s %-25s %-12s %-12s %-20s %10s %-20s%n",
                        "ID", "Name", "Date", "Status", "Type", "Cost", "Provider");
                System.out.println("-".repeat(110));
                for (Service s : services) {
                    System.out.printf("%-5d %-25s %-12s %-12s %-20s %10.2f %-20s%n",
                            s.getId(), s.getName(), s.getDate(),
                            s.getStatus(), s.getServiceType(),
                            s.getCost(), s.getProviderName());
                }
                System.out.println("-".repeat(110));
                System.out.println("Total records: " + services.size());
            }
        } catch (Exception e) {
            System.err.println("Display failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    // ──────────────────────────────────────────────────────────────
    //  MAIN
    // ──────────────────────────────────────────────────────────────

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║   Maven Hibernate HQL Demo – Service Entity          ║");
        System.out.println("║   Package  : com.klef.fsad.exam                      ║");
        System.out.println("║   Database : fsadexam                                ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");

        SessionFactory sf = HibernateUtil.getSessionFactory();

        try {
            // ── STEP 1: Show table before inserts ─────────────────
            System.out.println("\n[Before Insert]");
            displayAllServices(sf);

            // ── STEP 2: Insert 5 records (persistent objects) ─────
            insertRecords(sf);

            // ── STEP 3: Show records after insert ─────────────────
            System.out.println("\n[After Insert]");
            displayAllServices(sf);

            // ── STEP 4: Update record with ID = 2 ─────────────────
            //    Change Name  : "Annual Maintenance" → "Quarterly Maintenance"
            //    Change Status: "Pending"            → "Active"
            updateNameAndStatus(sf, 2, "Quarterly Maintenance", "Active");

            // ── STEP 5: Update record with ID = 3 ─────────────────
            //    Change Name  : "Cloud Migration" → "Hybrid Cloud Migration"
            //    Change Status: "Inactive"        → "Pending"
            updateNameAndStatus(sf, 3, "Hybrid Cloud Migration", "Pending");

            // ── STEP 6: Show records after updates ────────────────
            System.out.println("\n[After Updates]");
            displayAllServices(sf);

        } finally {
            HibernateUtil.shutdown();
        }

        System.out.println("\n✔ ClientDemo execution completed.");
    }
}
