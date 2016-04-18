package de.neemann.digital.draw.shapes;

import de.neemann.digital.core.Observer;
import de.neemann.digital.core.element.ElementAttributes;
import de.neemann.digital.core.element.Keys;
import de.neemann.digital.core.element.PinDescription;
import de.neemann.digital.draw.elements.IOState;
import de.neemann.digital.draw.elements.Pin;
import de.neemann.digital.draw.elements.Pins;
import de.neemann.digital.draw.graphics.*;

import static de.neemann.digital.draw.shapes.GenericShape.SIZE;
import static de.neemann.digital.draw.shapes.GenericShape.SIZE2;

/**
 * The Tunnel shape
 *
 * @author hneemann
 */
public class TunnelShape implements Shape {

    private final PinDescription input;
    private final String label;

    /**
     * Creates a new instance
     *
     * @param attr    the attributes
     * @param inputs  the inputs
     * @param outputs the outputs
     */
    public TunnelShape(ElementAttributes attr, PinDescription[] inputs, PinDescription[] outputs) {
        input = inputs[0];
        label = attr.get(Keys.NETNAME);
    }

    @Override
    public Pins getPins() {
        return new Pins().add(new Pin(new Vector(0, 0), input));
    }

    @Override
    public InteractorInterface applyStateMonitor(IOState ioState, Observer guiObserver) {
        return null;
    }

    @Override
    public void drawTo(Graphic gr, boolean highLight) {
        gr.drawPolygon(new Polygon(true)
                .add(0, 0)
                .add(SIZE, SIZE2)
                .add(SIZE, -SIZE2), Style.NORMAL);
        Vector pos = new Vector(SIZE + SIZE2, 0);
        gr.drawText(pos, pos.add(1, 0), label, Orientation.LEFTCENTER, Style.SHAPE_PIN);
    }
}