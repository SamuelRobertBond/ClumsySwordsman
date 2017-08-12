package Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;

public class RotationComponent implements Component{

	public RevoluteJoint joint;
	
	/**
	 * Rotation Component for Box2d Bodies
	 * @param sword fixture
	 */
	public RotationComponent(Body sword, RevoluteJoint joint) {
		this.joint = joint;
	}

	
}
