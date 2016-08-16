package s3.util;

public class Pair<T1,T2>
{
	public T1 m_a;
	public T2 m_b;
	public double cost;
	public double heuristic;
	public Pair<T1,T2> parent;

	public Pair(T1 a,T2 b)
	{
		m_a = a;
		m_b = b;
	}

	public boolean equals(Object o) 
	{
		Pair<T1, T2> p= (Pair<T1,T2>) o;
		if (!m_a.equals(p.m_a) ||
				!m_b.equals(p.m_b)) return false;
		return true;
	}
}
