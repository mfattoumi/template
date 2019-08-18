package org.cni.intranet.dao;
/**/
import java.util.List;

import org.cni.intranet.entities.Position;

public interface PositionDao {
	public Position addPosition(Position position);
	public Position deletePosition(int id);
	public Position updatePosition(Position position);
	public Position getPositionById(int id);
	public List<Position> getAllPositions();
}