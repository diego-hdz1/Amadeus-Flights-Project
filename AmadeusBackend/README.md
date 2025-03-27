# Here I will put all of the information

I will add the steps to set up the .env files here

## How to set up your API secrets 

For Linux and MacOS we can use the terminal to define the environment variables.
If you want just for temporal session:

export API_SECRET_KEY="your_api_key"

If you want to store it permanently, we can added to ~./bashrc

export API_SECRET_KEY="your_api_key" >>  ~./bashrc
source  ~./bashrc

Now in your program we can use the following command to obtain the value 

System.getenv("API_SECRET_KEY");