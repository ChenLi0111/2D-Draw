import java.io.Serializable;
import java.util.ArrayList;
import java.awt.*;
import java.awt.geom.*;
import javax.vecmath.*;

// simple shape model class
class Shape implements Serializable {

    // shape points
    private ArrayList<Point2d> points;

    public void clearPoints() {
        points = new ArrayList<Point2d>();
        pointsChanged = true;
    }

    // add a point to end of shape
    public void addPoint(Point2d p) {
        if (points == null) clearPoints();
        points.add(p);
        pointsChanged = true;
    }

    // add a point to end of shape
    public void addPoint(double x, double y) {
        if (points == null) clearPoints();
        addPoint(new Point2d(x, y));
    }

    public ArrayList<Point2d> get_points() {
        return points;
    }

    // shape is polyline or polygon
    private Boolean isClosed = false;

    // if polygon is filled or not
    private Boolean isFilled = false;

    // drawing attributes
    private Color colour = Color.red;
    private float strokeThickness = 3.0f;

    public void set_thickness(float f) {
        strokeThickness = f;
    }

    public Color getColour() {
        return colour;
    }

    public void setColour(Color colour) {
        this.colour = colour;
    }

    public float getStrokeThickness() {
        return strokeThickness;
    }

    // shape's transform

    private float scale = 1.0f;

    public void setScale(float scale){
        this.scale = scale;
    }

    // some optimization to cache points for drawing
    private Boolean pointsChanged = false; // dirty bit
    private int[] xpoints, ypoints;
    private int npoints = 0;

    void cachePointsArray() {
        xpoints = new int[points.size()];
        ypoints = new int[points.size()];
        for (int i = 0; i < points.size(); i++) {
            xpoints[i] = (int)points.get(i).x;
            ypoints[i] = (int)points.get(i).y;
        }
        npoints = points.size();
        pointsChanged = false;
    }


    // let the shape draw itself
    // (note this isn't good separation of shape View from shape Model)
    public void draw(Graphics2D g2) {

        // don't draw if points are empty (not shape)
        if (points == null) return;

        // see if we need to update the cache
        if (pointsChanged) cachePointsArray();

        // save the current g2 transform matrix 
        AffineTransform M = g2.getTransform();

        // multiply in this shape's transform
        // (uniform scale)
        g2.scale(scale, scale);

        // call drawing functions
        g2.setColor(colour);
        if (isFilled) {
            g2.fillPolygon(xpoints, ypoints, npoints);
        } else {
            // can adjust stroke size using scale
            g2.setStroke(new BasicStroke(strokeThickness / scale));
            if (isClosed)
                g2.drawPolygon(xpoints, ypoints, npoints);
            else
                g2.drawPolyline(xpoints, ypoints, npoints);
        }
    }
}
