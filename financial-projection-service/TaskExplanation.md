# My Approach to Solving the Problem Statement

## Introduction

The goal of this project was to analyze the structure of a provided Excel sheet and create a compact data structure that captures the essential financial information. Instead of just saving all the cell values, I focused on understanding the relationships between different cells and synthesizing the data.

## Approach

### Understanding the Sheet Structure

1. **Identified the Key Sections**: I started by looking at the Excel sheet to find the main sections, such as revenues and expenses. This involved checking for headers that indicate different parts of the budget.

2. **Recognized Patterns**: I looked for patterns in the layout, like repeated headers or similar data formats, to understand how the data was organized.

### Defining Relationships between the cells

1. **Hierarchical Relationships**: I was checking to see if there were categories and subcategories, such as total sales being divided into product sales and service sales. This helped me organize the data better.

2. **Formula-Based Relationships**: I checked for formulas that connect cells, like totals calculated from other values. Knowing these relationships was important for accurate data synthesis.

### Algorithm Design

#### Step 1: Data Parsing

- **Iterate Over Rows and Columns**: I used loops to go through each row and column in the sheet. I identified headers and categories by looking for non-numeric values.
- **Capture Values**: For each category I found, I collected the associated values needed for calculations.

#### Step 2: Relationship Mapping

- **Mapped Categories to Values**: I created a mapping of categories to their corresponding values using a Map function. This made it easy to access and work with the data.
- **Captured the structural Hierarchies**: For categories that had subcategories, I set up nested mappings to represent the hierarchy clearly.

#### Step 3: Data Synthesis

- **Calculating the projections**: Using the mapped relationships, I calculated totals and other important numbers, making sure to apply any necessary formulas.
- **Compact Representation**: I organized the data into a compact structure that captures the key parts of the budget, in the form of a JSON object.

