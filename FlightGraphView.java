import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class FlightGraphView extends JPanel {
    private static final int VERTEX_RADIUS = 16; 
    
    private final Map<String, LinkedList<String>> adjList;
    private final Map<String, Point> coordinates;

    public FlightGraphView(Map<String, LinkedList<String>> adjList) {
        this.adjList = adjList;
        this.coordinates = new HashMap<>();
        
        setBackground(Color.WHITE); 
        setPreferredSize(new Dimension(750, 450)); 
        
        // Map MAS airports to fixed X/Y coordinates
        coordinates.put("Langkawi", new Point(100, 100));
        coordinates.put("Penang", new Point(90, 230)); 
        coordinates.put("Kuala Lumpur", new Point(250, 300));
        coordinates.put("Johor Bahru", new Point(350, 400));
        coordinates.put("Kuching", new Point(500, 320));
        coordinates.put("Miri", new Point(650, 260)); 
        coordinates.put("Kota Kinabalu", new Point(680, 120));
        coordinates.put("Sandakan", new Point(700, 220));
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics.create();
        try {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // 终极修复：强制开启高精度纯粹笔触，防止 Windows 缩放导致线条渲染错位
            g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
            
            // 1. Draw Edges
            g.setColor(Color.DARK_GRAY); 
            // 终极修复：改回 1.0f 细线，彻底避开大于 1 像素时的“空心线”渲染 Bug
            g.setStroke(new java.awt.BasicStroke(1.0f)); 
            
            java.util.Set<String> drawnEdges = new java.util.HashSet<>();

            for (String fromCity : adjList.keySet()) {
                Point from = getCoordinate(fromCity);
                
                for (String toCity : adjList.get(fromCity)) {
                    Point to = getCoordinate(toCity);
                    
                    String route1 = fromCity + "-" + toCity;
                    String route2 = toCity + "-" + fromCity;
                    
                    if (!drawnEdges.contains(route1) && !drawnEdges.contains(route2)) {
                        drawnEdges.add(route1); 
                        g.drawLine(from.x, from.y, to.x, to.y);
                    }
                }
            }

            // 2. Draw Vertices
            FontMetrics metrics = g.getFontMetrics();
            for (String city : adjList.keySet()) {
                Point p = getCoordinate(city);
                int x = p.x;
                int y = p.y;
                
                g.setColor(new Color(70, 130, 180)); 
                g.fillOval(x - VERTEX_RADIUS, y - VERTEX_RADIUS, VERTEX_RADIUS * 2, VERTEX_RADIUS * 2);
                
                g.setColor(Color.BLACK); 
                g.drawOval(x - VERTEX_RADIUS, y - VERTEX_RADIUS, VERTEX_RADIUS * 2, VERTEX_RADIUS * 2);
                
                g.drawString(city, x - metrics.stringWidth(city) / 2, y - VERTEX_RADIUS - 4); 
            }
        } finally {
            g.dispose();
        }
    }
    
    private Point getCoordinate(String city) {
        return coordinates.computeIfAbsent(city, k -> 
            new Point((int)(Math.random() * 600 + 50), (int)(Math.random() * 350 + 50))
        );
    }
    
    public static void createAndShowGui(Map<String, LinkedList<String>> adjList) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("MAS Flight Network Map");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
            frame.add(new FlightGraphView(adjList));
            frame.pack(); 
            frame.setLocationRelativeTo(null); 
            frame.setVisible(true); 
        });
    }
}