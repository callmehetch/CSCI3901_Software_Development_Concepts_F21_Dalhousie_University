
//Class for storing the list to be returned
public class listToReturn {
    //-------------------Variables to store the list data to be returned---------------------------------
    //Variable to store distance of vertices
    private int dis;
    //Variable to store way of travelling
    private String way;
    //Variable to store the vertex
    private String vertex;
    //Variable to store city name
    private String city;
    //---------------------------Constructures to store data related to list to be returned---------------------------------------------
    public listToReturn(String way, int dis, String vertex, String city) {
        //Setting the way to way argument passed
        setWay(way);
        //Setting the dis to dis argument passed
        setDis(dis);
        //Setting the vertex to vertex argument passed
        setVertex(vertex);
        //Setting the city to city argument passed
        setCity(city);
    }
    //--------------------Setters and Getters-----------------------------
    //Getter for distance between vertices 
    public int getDis() {
        //return the distance
        return dis;
    }
    //Setter for distance between vertices
    public void setDis(int dis) {
        //set the distance
        this.dis = dis;
    }
    //Getter for way of travelling
    public String getWay() {
        //return the way of travelling
        return way;
    }
    //Setter for way of travelling
    public void setWay(String way) {
        //set the way of travelling
        this.way = way;
    }
    //Getter for vertices
    public String getVertex() {
        //return the vertex
        return vertex;
    }
    //Setter for vertices
    public void setVertex(String vertex) {
        //set the vertex
        this.vertex = vertex;
    }
    //Getter for city name
    public String getCity() {
        //get the city name
        return city;
    }
    //Setter for city name
    public void setCity(String city) {
        //set the city name
        this.city = city;
    }
}
