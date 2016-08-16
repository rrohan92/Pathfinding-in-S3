package s3.ai;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import s3.base.S3;
import s3.entities.S3PhysicalEntity;
import s3.util.Pair;

public class AStar 
{

	List<Pair<Double, Double>> open = new ArrayList<Pair<Double,Double>>();
	List<Pair<Double, Double>> closed = new ArrayList<Pair<Double,Double>>();
	List<Pair<Double, Double>> path = new ArrayList<Pair<Double,Double>>();


	public static int pathDistance(double start_x, double start_y, double goal_x, double goal_y,
			S3PhysicalEntity i_entity, S3 the_game)
	{
		AStar a = new AStar(start_x,start_y,goal_x,goal_y,i_entity,the_game);
		List<Pair<Double, Double>> path = a.computePath();
		if (path!=null) return path.size();
		return -1;
	}

	public AStar(double start_x, double start_y, double goal_x, double goal_y,
			S3PhysicalEntity i_entity, S3 the_game) 
	{

		Pair<Double,Double> start = new Pair<Double, Double>(start_x, start_y);
		Pair<Double,Double> goal = new Pair<Double, Double>(goal_x, goal_y);

		start.cost = 0;
		start.heuristic = Manhattan_distance(start, goal);
		start.parent = null;

		open.add(start);

		while(!open.isEmpty())
		{
			Pair<Double, Double> N = open.remove(lowest_pair());

			if(final_goal(N, goal))
			{
				path.add(N);
				while(N.parent != null)
				{
					Pair<Double, Double> temp = N.parent;
					path.add(temp);
					N = temp;					
				}
				break;
			}

			closed.add(N);

			List<Pair<Double, Double>> children = get_children(N, the_game);

			for(int i=0; i<children.size(); i++)
			{
				Pair<Double, Double> M = children.get(i);
				M.parent = N;
				M.cost = N.cost + 1;
				M.heuristic = Manhattan_distance(M, goal);
				open.add(M);
			}
		}

	}

	public List<Pair<Double, Double>> computePath()
	{
		Collections.reverse(path);

		return path;

	}

	public List<Pair<Double, Double>> get_children(Pair<Double, Double> N, S3 game)
	{
		List<Pair<Double, Double>> children = new ArrayList<Pair<Double,Double>>();
		double x = N.m_a;
		double y = N.m_b;

		Pair<Double,Double> child_1 = new Pair<Double, Double>(x+1, y);


		if(game.isSpaceFree(1, (int)(x+1), (int)y) && (not_equal(child_1, open)) && (not_equal(child_1, closed)))
			children.add(child_1);

		Pair<Double,Double> child_2 = new Pair<Double, Double>(x-1, y);

		if(game.isSpaceFree(1, (int)(x-1), (int)y) && (not_equal(child_2,open)) && (not_equal(child_2, closed)))
			children.add(child_2);


		Pair<Double,Double> child_3 = new Pair<Double, Double>(x, y+1);

		if(game.isSpaceFree(1, (int)x, (int)(y+1)) && (not_equal(child_3,open)) && (not_equal(child_3,closed)))
			children.add(child_3);


		Pair<Double,Double> child_4 = new Pair<Double, Double>(x, y-1);

		if(game.isSpaceFree(1, (int)x, (int)(y-1)) && (not_equal(child_4,open)) && (not_equal(child_4,closed)))		
			children.add(child_4);
		
		Pair<Double,Double> child_5 = new Pair<Double, Double>(x+1, y+1);


		if(game.isSpaceFree(1, (int)(x+1), (int)(y+1)) && (not_equal(child_5, open)) && (not_equal(child_5, closed)))
			children.add(child_5);

		Pair<Double,Double> child_6 = new Pair<Double, Double>(x+1, y-1);

		if(game.isSpaceFree(1, (int)(x+1), (int)(y-1)) && (not_equal(child_6,open)) && (not_equal(child_6, closed)))
			children.add(child_6);


		Pair<Double,Double> child_7 = new Pair<Double, Double>(x-1, y+1);

		if(game.isSpaceFree(1, (int)(x-1), (int)(y+1)) && (not_equal(child_7,open)) && (not_equal(child_7,closed)))
			children.add(child_7);


		Pair<Double,Double> child_8 = new Pair<Double, Double>(x-1, y-1);

		if(game.isSpaceFree(1, (int)(x-1), (int)(y-1)) && (not_equal(child_8,open)) && (not_equal(child_8,closed)))		
			children.add(child_8);


		return children;
	}

	public boolean not_equal(Pair<Double,Double> pair, List<Pair<Double,Double>> list)
	{
		for(int i=0; i<list.size(); i++)
		{
			Pair<Double,Double> temp = list.get(i);
			if(pair.equals(temp))
				return false;
		}
		return true;
	}


	public boolean final_goal(Pair<Double,Double> N, Pair<Double,Double> goal)
	{
		if(N.equals(goal))
			return true;
		else
			return false;
	}

	public int lowest_pair()
	{
		double min = Double.POSITIVE_INFINITY;

		for(int i=0; i<open.size(); i++)
		{
			Pair<Double, Double> node = open.get(i);
			double temp = node.cost + node.heuristic;

			if(temp < min)
				min = temp;
		}

		for(int i=0; i<open.size(); i++)
		{
			Pair<Double, Double> node = open.get(i);
			double temp = node.cost + node.heuristic;

			if(temp == min)
				return i;	
		}
		return 0;

	}

	public double Manhattan_distance(Pair<Double, Double> start, Pair<Double, Double> goal)
	{
		double dx = Math.abs(start.m_a - goal.m_a);
		double dy = Math.abs(start.m_b - goal.m_b);

		return 100*(dx + dy);
	}

}
