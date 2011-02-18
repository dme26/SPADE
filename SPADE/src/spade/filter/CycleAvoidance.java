/*
--------------------------------------------------------------------------------
SPADE - Support for Provenance Auditing in Distributed Environments.
Copyright (C) 2011 SRI International

This program is free software: you can redistribute it and/or
modify it under the terms of the GNU General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program. If not, see <http://www.gnu.org/licenses/>.
--------------------------------------------------------------------------------
 */

package spade.filter;

import spade.core.AbstractFilter;
import spade.core.AbstractEdge;
import spade.core.AbstractVertex;
import java.util.HashMap;
import java.util.HashSet;

public class CycleAvoidance extends AbstractFilter {

    private HashMap ancestors;

    public CycleAvoidance() {
        ancestors = new HashMap();
    }

    @Override
    public void putVertex(AbstractVertex incomingVertex) {
        putInNextFilter(incomingVertex);
    }

    @Override
    public void putEdge(AbstractEdge incomingEdge) {
        AbstractVertex sourceVertex = incomingEdge.getSrcVertex();
        AbstractVertex destinationVertex = incomingEdge.getDstVertex();
        if (ancestors.containsKey(destinationVertex)) {
            HashSet tempSet = (HashSet) ancestors.get(destinationVertex);
            if (tempSet.contains(sourceVertex) == false) {
                tempSet.add(sourceVertex);
                putInNextFilter(incomingEdge);
            }
        } else {
            HashSet tempSet = new HashSet();
            tempSet.add(sourceVertex);
            ancestors.put(destinationVertex, tempSet);
            putInNextFilter(incomingEdge);
        }
    }
}