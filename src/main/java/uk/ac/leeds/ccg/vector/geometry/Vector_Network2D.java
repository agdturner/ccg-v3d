/*
 * Copyright 2019 Andy Turner, University of Leeds.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.leeds.ccg.vector.geometry;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import uk.ac.leeds.ccg.grids.d2.grid.Grids_GridNumber;
import uk.ac.leeds.ccg.vector.core.Vector_Environment;

/**
 * A network comprises of a map of connections with of points as keys and sets
 * of Connections as values. Each Connection comprises of a point and a
 * capacity.
 */
public class Vector_Network2D extends Vector_Geometry2D {

    public static int DefaultCapacity = 1;
    public static int DefaultType = 0;
    public HashMap<Vector_Point2D, HashSet<Connection>> connections;

    public Vector_Network2D(Vector_Environment e) {
        super(e);
        connections = new HashMap<>();
    }

    public Vector_Network2D(Vector_Environment e, Grids_GridNumber g) {
        super(e);
        connections = new HashMap<>();
        HashSet<Vector_Point2D> neighbouringPoints;
        for (long row = 0; row < g.getNRows(); row++) {
            for (long col = 0; col < g.getNCols(); col++) {
                Vector_Point2D p = new Vector_Point2D(e, g.getCellX(col),
                        g.getCellY(row));
                HashSet<Connection> s = new HashSet<>();
                neighbouringPoints = getNeighbouringPoints(e, g, row, col);
                for (Vector_Point2D b_Point2D : neighbouringPoints) {
                    Connection c = new Connection(b_Point2D, DefaultType,
                            DefaultCapacity);
                    s.add(c);
                }
                connections.put(p, s);
            }
        }
    }

    /**
     * Adds the points and connections from {@code n} into this. If lowest is
     * true then for connections between the same start and end points of the
     * same type, the lowest value of these is maintained, otherwise the highest
     * is.
     *
     * @param n Vector_Network2D
     * @param operator If operator == 1, then min; operator == 2, then max;
     * operator == 3, then increment; operator == 4, then sum.
     */
    public void addToNetwork(Vector_Network2D n, int operator) {
        for (Vector_Point2D a : n.connections.keySet()) {
            if (connections.containsKey(a)) {
                HashSet<Connection> cs = connections.get(a);
                HashSet<Connection> ncs = n.connections.get(a);
                for (Connection c : ncs) {
                    // See if this breaks out as expected... May be going through all records unnecessarily!
                    // No work needed for checking type due to the nature of Connection.equals(Object)
                    if (cs.contains(c)) {
                        for (Connection t_Connection : cs) {
                            if (t_Connection.equals(c)) {
                                // See if this breaks out as desired... May be going through all records unnecessarily!
                                switch (operator) {
                                    case 0:
                                        if (t_Connection.capacity > c.capacity) {
                                            cs.add(c);
                                        }
                                        break;
                                    case 1:
                                        if (t_Connection.capacity < c.capacity) {
                                            cs.add(c);
                                        }
                                        break;
                                    default:
                                        t_Connection.setCapacity(t_Connection.capacity + c.capacity);
                                        break;
                                }
                            }
                        }
                    } else {
                        cs.add(c);
                        break;
                    }
                }
            } else {
                connections.put(a, n.connections.get(a));
            }
        }
    }

    /**
     * Adds {@code p} and {@code s} to {@link #connections}
     *
     * @param p The Point to connect
     * @param s The set of connections.
     */
    public void addToNetwork(Vector_Point2D p,            HashSet<Connection> s) {
        if (connections.containsKey(p)) {
            ((HashSet) connections.get(p)).addAll(s);
        } else {
            connections.put(p, s);
        }
    }

    /**
     * Updates {@link #connections} by either adding a new mapping or modifying
     * the existing one.
     *
     * @param p A point.
     * @param c A connection
     * @param operator: operator 0, min; operator 1, max; operator default, sum.
     */
    public void addToNetwork(Vector_Point2D p, Connection c, int operator) {
        //if (!p.equals(c.location)){
        if (connections.containsKey(p)) {
            HashSet<Connection> s = (HashSet<Connection>) connections.get(p);
            if (s.contains(c)) {
                for (Connection c2 : s) {
                    if (c2.equals(c)) {
                        switch (operator) {
                            case 0:
                                if (c2.capacity > c.capacity) {
                                    s.add(c);
                                }
                                break;
                            case 1:
                                if (c2.capacity < c.capacity) {
                                    s.add(c);
                                }
                                break;
                            default:
                                c2.setCapacity(c2.capacity + c.capacity);
                        }
                    }
                }
            }
        } else {
            HashSet<Connection> s = new HashSet<>();
            s.add(c);
            connections.put(p, s);
        }
        //}
    }

    /**
     * Adds a connection with a Default type and capacity using the operator.
     *
     * @param toConnect Vector_Point2D
     * @param toConnectTo Vector_Point2D
     * @param operator: operator 0, min; operator 1, max; operator default, sum.
     */
    public void addToNetwork(Vector_Point2D toConnect,
            Vector_Point2D toConnectTo, int operator) {
        if (!toConnect.equals(toConnectTo)) {
            Connection c = new Connection(toConnectTo, DefaultType,
                    DefaultCapacity);
            addToNetwork(toConnect, c, operator);
        }
    }

    /**
     * Adds a connection with a Default type and Value using the operator.
     *
     * @param toConnect Vector_Point2D
     * @param toConnectTo Vector_Point2D
     */
    public void addToNetwork(Vector_Point2D toConnect, Vector_Point2D toConnectTo) {
        if (!toConnect.equals(toConnectTo)) {
            addToNetwork(toConnect, toConnectTo, 2);
        }
    }

    @Override
    public String toString() {
        return "connections.keySet().size() " + connections.keySet().size();
    }

    public static HashSet<Vector_Point2D> getNeighbouringPoints(
            Vector_Environment ve, Grids_GridNumber g, long row, long col) {
        HashSet<Vector_Point2D> r = new HashSet<>();
        long arow = row - 1;
        long acol = col - 1;
        if (g.isInGrid(arow, acol)) {
            r.add(new Vector_Point2D(ve, g.getCellX(acol), g.getCellY(arow)));
        }
        arow = row - 1;
        acol = col;
        if (g.isInGrid(arow, acol)) {
            r.add(new Vector_Point2D(ve, g.getCellX(acol), g.getCellY(arow)));
        }
        arow = row - 1;
        acol = col + 1;
        if (g.isInGrid(arow, acol)) {
            r.add(new Vector_Point2D(ve, g.getCellX(acol), g.getCellY(arow)));
        }
        arow = row;
        acol = col - 1;
        if (g.isInGrid(arow, acol)) {
            r.add(new Vector_Point2D(ve, g.getCellX(acol), g.getCellY(arow)));
        }
        arow = row;
        acol = col + 1;
        if (g.isInGrid(arow, acol)) {
            r.add(new Vector_Point2D(ve, g.getCellX(acol), g.getCellY(arow)));
        }
        arow = row + 1;
        acol = col - 1;
        if (g.isInGrid(arow, acol)) {
            r.add(new Vector_Point2D(ve, g.getCellX(acol), g.getCellY(arow)));
        }
        arow = row + 1;
        acol = col;
        if (g.isInGrid(arow, acol)) {
            r.add(new Vector_Point2D(ve, g.getCellX(acol), g.getCellY(arow)));
        }
        arow = row + 1;
        acol = col + 1;
        if (g.isInGrid(arow, acol)) {
            r.add(new Vector_Point2D(ve, g.getCellX(acol), g.getCellY(arow)));
        }
        return r;
    }

    @Override
    public Vector_Envelope2D getEnvelope2D() {
        Vector_Envelope2D r = new Vector_Envelope2D(e);
        Set<Vector_Point2D> cs = connections.keySet();
        Iterator<Vector_Point2D> ite = cs.iterator();
        while (ite.hasNext()) {
            Vector_Point2D a = ite.next();
            r = r.envelope(a.getEnvelope2D());
            HashSet<Connection> cs2 = connections.get(a);
            Iterator<Connection> ite2 = cs2.iterator();
            while (ite2.hasNext()) {
                Vector_Point2D b = ite2.next().location;
                r = r.envelope(b.getEnvelope2D());
            }
        }
        return r;
    }

    @Override
    public void applyDecimalPlacePrecision() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public class Connection implements Serializable {

        public Vector_Point2D location;

        public int capacity;

        public int type;

        public Connection() {
        }

        public Connection(Vector_Point2D location, int type, int capacity) {
            this.location = location;
            this.type = type;
            this.capacity = capacity;
        }

        public void setCapacity(int capacity) {
            this.capacity = capacity;
        }

        /**
         * This ignores capacity, so two connections are the same irrespective
         * of capacity.
         *
         * @param o Object
         * @return boolean
         */
        @Override
        public boolean equals(Object o) {
            if (o instanceof Connection) {
                Connection c = (Connection) o;
                return location.equals(c.location)
                        && type == c.type;
            }
            return false;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 73 * hash + (this.location != null ? this.location.hashCode() : 0);
            hash = 73 * hash + this.type;
            return hash;
        }
    }
}
