# Data Platform Tutorial: Exploratory Notebook

### Objectives
* Load data from flat file
* Perform basic cleaning & exploration
* Create basic visualizations
* Perform some light text processing

### Prerequisites
* Completed Ingest / Transformation tutorial sections.
* CSV file of tweets in `/tweets-batch` folder.

## Do This First

### Preferred Option: Connect to Azure Notebooks

First, visit our notebook library: https://notebooks.azure.com/ben-svds/libraries/svds-data-platform-tutorial.

Make sure you've logged in with your Azure Notebooks credentials (create an account if you don't already have one). 

Clone and run the shared library - the notebooks library will be cloned to your account. 

### Alternate Option: Launch Jupyter Notebook Container

You'll need to include the absolute path to the `data-platform-tutorial` repo in your `docker run` command.

    $ docker pull jupyter/pyspark-notebook
    $ docker run -it --rm -p 8888:8888 -v <path/to/data-platform-tutorial>:/home/jovyan/work jupyter/pyspark-notebook

Copy/paste the URL produced by the previous command into your browser to connect to Jupyter.

Navigate to the `/notebooks` folder.

## Pyspark Exploratory Notebook

We will use Pyspark and Pandas to perform a basic exploration of the Twitter data we've collected.

### Start with `NotebookTutorial.ipynb`

This file has been stubbed out for this tutorial. Open it via the Jupyter interface to get started.

If you run into problems, `notebooks/NotebookFinal.ipynb` has the completed code.
