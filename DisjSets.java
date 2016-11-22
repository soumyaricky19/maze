package maze;

public class DisjSets
{
        /** Construct the disjoint sets object.
         * @param numElements the initial number of disjoint sets.
         */
    private int [ ] s;
    
    public DisjSets( int numElements )
    {
        s = new int [ numElements ];
        for( int i = 0; i < s.length; i++ )
        	s[ i ] = -1;
    }

        /**   Union two disjoint sets.  
         *     Assume root1 and root2 are distinct and represent set names.
         * @param root1 the root of set 1.
         * @param root2 the root of set 2.          */
        public void union( int root1, int root2 )
        {
            s[ root2 ] = root1;
        }
        public int find( int x )
        {
            if( s[ x ] < 0 )
                return x;
            else
                return find( s[ x ] );
        }
        public void display()
        {
        	for( int i = 0; i < s.length; i++ )
        		if (s[ i ] == -1)
            	System.out.print( i +",");
        }
}
