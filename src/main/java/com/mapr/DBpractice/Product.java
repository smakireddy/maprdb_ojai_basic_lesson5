package com.mapr.DBpractice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import org.ojai.types.ODate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Product
{
	private String id;
	private String name;
	private String description;
	private Double sellingPrice;
	private Double costPrice;

	@JsonProperty("_id")
	public String getId()
		{
			return id;
		}

	public void setId(String id)
		{
			this.id = id;
		}

	@JsonProperty("Name")
	public String getName()
		{
			return name;
		}

	public void setName(String name)
		{
			this.name = name;
		}

	@JsonProperty("Description")
	public String getDescription()
		{
			return description;
		}

	public void setDescription(String description)
		{
			this.description = description;
		}

	@JsonProperty("Selling Price")
	public Double getSellingPrice()
		{
			return sellingPrice;
		}

	public void setSellingPrice(Double sellingPrice)
		{
			this.sellingPrice = sellingPrice;
		}

	@JsonProperty("Cost Price")
	public Double getCostPrice()
		{
			return costPrice;
		}

	public void setCostPrice(Double costPrice)
		{
			this.costPrice = costPrice;
		}
	
}
