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
package uk.ac.leeds.ccg.vector.visualisation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
//import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
//import javax.swing.JApplet;
import javax.swing.JFrame;
import uk.ac.leeds.ccg.vector.core.Vector_Environment;
import uk.ac.leeds.ccg.vector.geometry.Vector_Envelope2D;
import uk.ac.leeds.ccg.vector.geometry.Vector_Network2D;
import uk.ac.leeds.ccg.vector.geometry.Vector_Network2D.Connection;
import uk.ac.leeds.ccg.vector.geometry.Vector_Point2D;
//import uk.ac.leeds.ccg.;

/**
 * Class for rendering Vector_Network2D instances.
 */
public class Vector_RenderNetwork2D extends Vector_AbstractRender {

    public HashSet<Vector_Network2D> Networks;
    public Vector_Envelope2D Envelope;
    public BigDecimal _YRange_BigDecimal;
    public BigDecimal _XRange_BigDecimal;

    protected Vector_RenderNetwork2D(){}

    public Vector_RenderNetwork2D(
            Vector_Environment ve,
            JFrame f) {
        super(ve);
        _JFrame = f;
        _JFrame.getContentPane().add("Center", this);
        init();
        _JFrame.pack();
        _JFrame.setSize(new Dimension(this.Width, this.Height));
        _JFrame.setVisible(true);
    }

    public Vector_RenderNetwork2D(
            Vector_Environment ve,
            JFrame f,
            int width,
            int height,
            HashSet<Vector_Network2D> networks) {
        super(ve);
        this.Width = width;
        this.Height = height;
        _JFrame = f;
        this._JFrame.getContentPane().add("Center", this);
        this.init();
        _JFrame.pack();
        _JFrame.setSize(new Dimension(this.Width, this.Height));
        //_JFrame.pack();
        _JFrame.setVisible(true);
        this.Networks = networks;
    }

    public Vector_RenderNetwork2D(
            Vector_Environment ve,
            JFrame a_JFrame,
            int width,
            int height,
            HashSet<Vector_Network2D> _Network2D_HashSet,
            Vector_Envelope2D a_VectorEnvelope2D) {
        super(ve);
        this.Width = width;
        this.Height = height;
        _JFrame = a_JFrame;
        this._JFrame.getContentPane().add("Center", this);
        this.init();
        _JFrame.pack();
        _JFrame.setSize(new Dimension(this.Width, this.Height));
        //_JFrame.pack();
        _JFrame.setVisible(true);
        this.Networks = _Network2D_HashSet;
        this.Envelope = a_VectorEnvelope2D;
    }

    @Override
    public void paint(Graphics _Graphics) {
        super.paint(_Graphics);
        this._Graphics2D = (Graphics2D) _Graphics;
        // Draw
        draw();
        //drawNetwork2D();
    }

    public void draw(Graphics g) {
        this._Graphics2D = (Graphics2D) g;
        draw();
    }

    public void draw() {
        Iterator a_Iterator = Networks.iterator();
        Vector_Network2D a_Network2D;
        while (a_Iterator.hasNext()) {
            a_Network2D = (Vector_Network2D) a_Iterator.next();
            Set<Vector_Point2D> keys = a_Network2D.connections.keySet();
            Iterator bIterator;
            bIterator = keys.iterator();
            Vector_Point2D a_Point2D;
            HashSet<Connection> a_Connection_HashSet;
            while (bIterator.hasNext()) {
                a_Point2D = (Vector_Point2D) bIterator.next();
                a_Connection_HashSet = (HashSet<Connection>) a_Network2D.connections.get(a_Point2D);
                draw(
                        a_Point2D,
                        a_Connection_HashSet);
            }
        }
    }

    public void init_Envelope2D(){
        if (Networks != null){
            Iterator<Vector_Network2D> a_Network2D_HashSet_Iterator = Networks.iterator();
            Vector_Network2D a_Network2D;
            Envelope = new Vector_Envelope2D(ve);
            while (a_Network2D_HashSet_Iterator.hasNext()) {
                a_Network2D = a_Network2D_HashSet_Iterator.next();
                Envelope = Envelope.envelope(a_Network2D.getEnvelope2D());
            }
        }
    }



        public void draw(
            Vector_Point2D a_Point2D,
            HashSet<Connection> a_Connection_HashSet) {
            if (Envelope == null){
                init_Envelope2D();
            }
            _YRange_BigDecimal = Envelope.yMax.subtract(Envelope.yMin);
            _XRange_BigDecimal = Envelope.xMax.subtract(Envelope.xMin);
//        boolean handleOutOfMemoryError = _Environment._HandleOutOfMemoryError;
//        BigDecimal[] reportingDimensions = _Environment._reportingPopulationDensityAggregate_Grid2DSquareCellDouble.get_Dimensions(handleOutOfMemoryError);
//        BigDecimal[] networkDimensions = _Environment._network_Grid2DSquareCellDouble.get_Dimensions(handleOutOfMemoryError);
//        int scale = reportingDimensions[0].divide(networkDimensions[0]).intValue();
        BigDecimal width_BigDecimal = new BigDecimal(Width);
        BigDecimal height_BigDecimal = new BigDecimal(Height);

            this._Graphics2D.setPaint(Color.RED);
        int ax = ((a_Point2D.x.subtract(Envelope.xMin)).divide
                (_XRange_BigDecimal,10,RoundingMode.DOWN).multiply
                (width_BigDecimal)).intValue();
        int ay = ((a_Point2D.y.subtract(Envelope.yMin)).divide
                (_YRange_BigDecimal,10,RoundingMode.DOWN).multiply
                (height_BigDecimal)).intValue();
        int bx;
        int by;
        Iterator aIterator;
        aIterator = a_Connection_HashSet.iterator();
        while (aIterator.hasNext()) {
            Connection a_Connection = (Connection) aIterator.next();
            //bx = (int) a_Connection.location.x.intValue() * _Scale;
            //by = (int) a_Connection.location.y.intValue() * _Scale;
            //bx = (int) (a_Connection.location.x.doubleValue() * _Scale);
            //by = (int) (a_Connection.location.y.doubleValue() * _Scale);
            bx = ((a_Connection.location.x.subtract(Envelope.xMin)).divide
                    (_XRange_BigDecimal,10,RoundingMode.DOWN).multiply
                (width_BigDecimal)).intValue();
        by = ((a_Connection.location.y.subtract(Envelope.yMin)).divide
                (_YRange_BigDecimal,10,RoundingMode.DOWN).multiply
                (height_BigDecimal)).intValue();
            //this._Graphics2D.drawLine(ax, ay, bx, by);
        System.out.println(
                " ax " + ax +
                " ay " + ay +
                " bx " + bx +
                " by " + by);
            if (!(ax == bx && ay == by)){
                this._Graphics2D.drawLine(
                        ax,
                        Height - ay,
                        bx,
                        Height - by);
            }
        }
    }
}
