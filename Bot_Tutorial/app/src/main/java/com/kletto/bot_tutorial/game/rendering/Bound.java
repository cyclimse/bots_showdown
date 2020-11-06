package com.kletto.bot_tutorial.game.rendering;

import org.dyn4j.collision.AbstractBounds;
import org.dyn4j.collision.Bounds;
import org.dyn4j.collision.CollisionBody;
import org.dyn4j.geometry.AABB;
import org.dyn4j.geometry.Translatable;
import org.dyn4j.geometry.Vector2;
import org.dyn4j.resources.Messages;

/**
 * Represents a bounding region that is an Axis-Aligned bounding box.
 * <p>
 * This class compares its AABB with the AABB of the given body and returns true
 * if they do not overlap.
 * @author William Bittle
 * @version 4.0.0
 * @since 3.1.1
 */
public final class Bound extends AbstractBounds implements Bounds, Translatable {
    /** The local coordinates AABB */
    protected final AABB aabb;

    /**
     * Minimal constructor.
     * @param width the width of the bounds; must be greater than zero
     * @param height the height of the bounds; must be greater than zero
     * @throws IllegalArgumentException if either width or height are less than or equal to zero
     */
    public Bound(double width, double height) {
        if (width <= 0.0 || height <= 0.0) throw new IllegalArgumentException(Messages.getString("collision.bounds.axisAligned.invalidArgument"));
        this.aabb = new AABB(0, 0, width, height);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AxisAlignedBounds[Width=").append(this.aabb.getWidth())
                .append("|Height=").append(this.aabb.getHeight())
                .append("|Translation=").append(this.getTranslation())
                .append("]");
        return sb.toString();
    }

    /* (non-Javadoc)
     * @see org.dyn4j.collision.Bounds#isOutside(org.dyn4j.collision.CollisionBody)
     */
    @Override
    public boolean isOutside(CollisionBody<?> body) {
        Vector2 tx = this.transform.getTranslation();

        AABB aabbBounds = this.aabb.getTranslated(tx);
        AABB aabbBody = body.createAABB();

        // test the projections for overlap
        return !aabbBounds.overlaps(aabbBody);
    }

    /* (non-Javadoc)
     * @see org.dyn4j.collision.Bounds#isOutside(org.dyn4j.geometry.AABB)
     */
    @Override
    public boolean isOutside(AABB aabb) {
        Vector2 tx = this.transform.getTranslation();
        AABB aabbBounds = this.aabb.getTranslated(tx);

        // test the projections for overlap
        return !aabbBounds.overlaps(aabb);
    }

    /**
     * Returns the world space Axis-Aligned bounding box for this
     * bounds object.
     * @return {@link AABB}
     */
    public AABB getBounds() {
        // return the AABB in world coordinates
        return this.aabb.getTranslated(this.transform.getTranslation());
    }

    /**
     * Returns the width of the bounds.
     * @return double
     */
    public double getWidth() {
        return this.aabb.getWidth();
    }

    /**
     * Returns the height of the bounds.
     * @return double
     */
    public double getHeight() {
        return this.aabb.getHeight();
    }
}
