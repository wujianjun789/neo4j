/*
 * Copyright (c) 2002-2018 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.kernel.impl.newapi;

import org.neo4j.internal.kernel.api.Token;
import org.neo4j.internal.kernel.api.exceptions.KernelException;
import org.neo4j.internal.kernel.api.exceptions.LabelNotFoundKernelException;
import org.neo4j.internal.kernel.api.exceptions.PropertyKeyIdNotFoundKernelException;
import org.neo4j.internal.kernel.api.exceptions.schema.IllegalTokenNameException;
import org.neo4j.internal.kernel.api.exceptions.schema.TooManyLabelsException;
import org.neo4j.storageengine.api.StorageEngine;
import org.neo4j.storageengine.api.StoreReadLayer;

public class KernelToken implements Token
{
    private final StoreReadLayer store;

    public KernelToken( StorageEngine engine )
    {
        store = engine.storeReadLayer();
    }

    @Override
    public int labelGetOrCreateForName( String labelName ) throws IllegalTokenNameException, TooManyLabelsException
    {
        return store.labelGetOrCreateForName( checkValidTokenName( labelName ) );
    }

    @Override
    public int propertyKeyGetOrCreateForName( String propertyKeyName ) throws IllegalTokenNameException
    {
        return store.propertyKeyGetOrCreateForName( checkValidTokenName( propertyKeyName ) );
    }

    @Override
    public int relationshipTypeGetOrCreateForName( String relationshipTypeName ) throws IllegalTokenNameException
    {
        throw new UnsupportedOperationException( "not implemented" );
    }

    @Override
    public void labelCreateForName( String labelName, int id ) throws IllegalTokenNameException, TooManyLabelsException
    {
        throw new UnsupportedOperationException( "not implemented" );
    }

    @Override
    public String labelGetName( int token ) throws LabelNotFoundKernelException
    {
        return store.labelGetName( token );
    }

    @Override
    public int labelGetForName( String name )
    {
        return store.labelGetForName( name );
    }

    @Override
    public void propertyKeyCreateForName( String propertyKeyName, int id ) throws IllegalTokenNameException
    {
        throw new UnsupportedOperationException( "not implemented" );
    }

    @Override
    public void relationshipTypeCreateForName( String relationshipTypeName, int id ) throws IllegalTokenNameException
    {
        throw new UnsupportedOperationException( "not implemented" );
    }

    @Override
    public int nodeLabel( String name )
    {
        return store.labelGetForName( name );
    }

    @Override
    public int relationshipType( String name )
    {
        return store.relationshipTypeGetForName( name );
    }

    @Override
    public int propertyKey( String name )
    {
        return store.propertyKeyGetForName( name );
    }

    @Override
    public String propertyKeyGetName( int propertyKeyId ) throws PropertyKeyIdNotFoundKernelException
    {
        return store.propertyKeyGetName( propertyKeyId );
    }

    private String checkValidTokenName( String name ) throws IllegalTokenNameException
    {
        if ( name == null || name.isEmpty() )
        {
            throw new IllegalTokenNameException( name );
        }
        return name;
    }
}
