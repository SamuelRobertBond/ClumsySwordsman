package Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;

public class RotationComponent implements Component{

	public RevoluteJoint revoluteJoint;
	public WeldJoint weldJoint;
	
	/**
	 * Rotation Component for Box2d Bodies
	 * @param sword fixture
	 */
	public RotationComponent(Body sword, RevoluteJoint revoultejoint, WeldJoint weldJoint) {
		this.revoluteJoint =  revoultejoint;
		this.weldJoint = weldJoint;
	}

	
}
