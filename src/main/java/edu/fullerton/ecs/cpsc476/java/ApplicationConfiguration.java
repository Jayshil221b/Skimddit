package edu.fullerton.ecs.cpsc476.java;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApplicationConfiguration extends Configuration
{
	@JsonProperty
	private DataSourceFactory database = new DataSourceFactory();
	public DataSourceFactory getDataSourceFactory()
	{
		return database;
	}
}
