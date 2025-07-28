package com.app.neo4j;

import org.neo4j.driver.*;
import org.neo4j.driver.Record;

public class Neo4jConexion implements AutoCloseable {

    private final Driver driver;

    public Neo4jConexion(String uri, String user, String password) {
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }

    public void ejecutarYMostrar(String cypher, String variable) {
        try (Session session = driver.session()) {
            Result result = session.run(cypher);
            while (result.hasNext()) {
                Record record = result.next();
                System.out.println(record.get(variable).asString());
            }
        }
    }


    @Override
    public void close() {
        driver.close();
    }






    public static void main(String[] args) {
        try (Neo4jConexion conexion = new Neo4jConexion("neo4j+s://ddb6e642.databases.neo4j.io", "neo4j", "S03NIyJiBguLLzZfHVfPTPLSlManpgbsi5eqS-AImzA")) {

            try (Session session = conexion.driver.session()) {
                // CREACION DE GRAFO
                /*
                // Crear Bares
                session.run("CREATE (:Bar {nombre: 'BAR 1'})");
                session.run("CREATE (:Bar {nombre: 'BAR 2'})");
                session.run("CREATE (:Bar {nombre: 'BAR 3'})");

                // Crear Personas
                session.run("CREATE (:Persona {nombre: 'JORGE'})");
                session.run("CREATE (:Persona {nombre: 'JAVIER'})");
                session.run("CREATE (:Persona {nombre: 'JOSE'})");

                // Crear Cervezas
                session.run("CREATE (:Cerveza {nombre: 'ANDES'})");
                session.run("CREATE (:Cerveza {nombre: 'BRAHMA'})");
                session.run("CREATE (:Cerveza {nombre: 'QUILMES'})");

                // Relaciones: Sirve
                session.run("MATCH (b:Bar {nombre: 'BAR 1'}), (c:Cerveza {nombre: 'QUILMES'}) CREATE (b)-[:SIRVE]->(c)");
                session.run("MATCH (b:Bar {nombre: 'BAR 1'}), (c:Cerveza {nombre: 'BRAHMA'}) CREATE (b)-[:SIRVE]->(c)");
                session.run("MATCH (b:Bar {nombre: 'BAR 1'}), (c:Cerveza {nombre: 'ANDES'}) CREATE (b)-[:SIRVE]->(c)");
                session.run("MATCH (b:Bar {nombre: 'BAR 2'}), (c:Cerveza {nombre: 'QUILMES'}) CREATE (b)-[:SIRVE]->(c)");
                session.run("MATCH (b:Bar {nombre: 'BAR 3'}), (c:Cerveza {nombre: 'QUILMES'}) CREATE (b)-[:SIRVE]->(c)");
                session.run("MATCH (b:Bar {nombre: 'BAR 3'}), (c:Cerveza {nombre: 'ANDES'}) CREATE (b)-[:SIRVE]->(c)");

                // Relaciones: Le gusta
                session.run("MATCH (p:Persona {nombre: 'JORGE'}), (c:Cerveza {nombre: 'ANDES'}) CREATE (p)-[:LE_GUSTA]->(c)");
                session.run("MATCH (p:Persona {nombre: 'JORGE'}), (c:Cerveza {nombre: 'QUILMES'}) CREATE (p)-[:LE_GUSTA]->(c)");
                session.run("MATCH (p:Persona {nombre: 'JAVIER'}), (c:Cerveza {nombre: 'BRAHMA'}) CREATE (p)-[:LE_GUSTA]->(c)");
                session.run("MATCH (p:Persona {nombre: 'JOSE'}), (c:Cerveza {nombre: 'QUILMES'}) CREATE (p)-[:LE_GUSTA]->(c)");

                // Relaciones: Frecuenta
                session.run("MATCH (p:Persona {nombre: 'JORGE'}), (b:Bar {nombre: 'BAR 1'}) CREATE (p)-[:FRECUENTA]->(b)");
                session.run("MATCH (p:Persona {nombre: 'JORGE'}), (b:Bar {nombre: 'BAR 2'}) CREATE (p)-[:FRECUENTA]->(b)");
                session.run("MATCH (p:Persona {nombre: 'JAVIER'}), (b:Bar {nombre: 'BAR 2'}) CREATE (p)-[:FRECUENTA]->(b)");
                session.run("MATCH (p:Persona {nombre: 'JOSE'}), (b:Bar {nombre: 'BAR 3'}) CREATE (p)-[:FRECUENTA]->(b)");



                */


            }
        System.out.println("1. Listar todas las personas \n");
        conexion.ejecutarYMostrar("MATCH (p:Persona) RETURN p.nombre", "p.nombre");

        System.out.println("2. Todos los bares que sirven Quilmes \n");
        conexion.ejecutarYMostrar("MATCH (b:Bar)-[:SIRVE]->(c:Cerveza {nombre: 'QUILMES'}) RETURN b.nombre","b.nombre");

        System.out.println("3. Las cervezas que le gustan a José \n");
        conexion.ejecutarYMostrar("MATCH (p:Persona {nombre: 'JOSE'})-[:LE_GUSTA]->(c:Cerveza) RETURN c.nombre","c.nombre");

        System.out.println("4. Los bares que sirven la cerveza que le gusta a Jorge \n");
        conexion.ejecutarYMostrar("MATCH (p:Persona {nombre: 'JORGE'})-[:LE_GUSTA]->(c:Cerveza)<-[:SIRVE]-(b:Bar) RETURN DISTINCT b.nombre","b.nombre");

        System.out.println("5. Las personas que frecuentan bares que sirven alguna cerveza que les gusta \n");
        conexion.ejecutarYMostrar("MATCH (p:Persona)-[:FRECUENTA]->(b:Bar)-[:SIRVE]->(c:Cerveza)<-[:LE_GUSTA]-(p) RETURN DISTINCT p.nombre","p.nombre");

        System.out.println("6. Las personas que frecuentan los bares que sirven la cerveza que le gusta a Javier \n");
        conexion.ejecutarYMostrar("MATCH (p:Persona {nombre: 'JAVIER'})-[:LE_GUSTA]->(c:Cerveza)<-[:SIRVE]-(b:Bar)<-[:FRECUENTA]-(otra:Persona) RETURN DISTINCT otra.nombre","otra.nombre");






        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
