import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;


    public class DoublePQ {
        private DoubleCar [] pq;                    // store items at indices 1 to N

        private int N;                       // number of items on priority queue

        private String S11111;
        private String P1;//postion in pindexpq
        private String P3;
        private int P2;//positon in mindexpq
        private HashMap<String,Integer> pindexpq=new HashMap<String,Integer>();//store position ranked by price in PQ
        private HashMap<String,Integer> mindexpq=new HashMap<String,Integer>();//store position ranked by mileage in PQ
        
        public HashMap<Integer,Car>ci=new HashMap<Integer,Car>();


        /**
         * Initializes an empty priority queue with the given initial capacity.
         *
         * @param  initCapacity the initial capacity of this priority queue
         */
        public DoublePQ(int initCapacity) {
            pq = new DoubleCar [initCapacity + 1];
            N = 0;
        }

        /**
         * Initializes an empty priority queue.
         */
        public DoublePQ() {
            this(1);
        }




        /**
         * Initializes a priority queue from the array of keys.
         * <p>
         * Takes time proportional to the number of keys, using sink-based heap construction.
         *
         * @param  keys the array of keys
    
        public DoublePQ(DoubleCar [] keys) {
            N = keys.length;
            pq = new DoubleCar[keys.length + 1];
            for (int i = 0; i < N; i++)
                pq[i+1] = keys[i];
            for (int k = N/2; k >= 1; k--)
                sink(k);
            assert isMinHeap();
        }
        */
        /**
         * Returns true if this priority queue is empty.
         *
         * @return <tt>true</tt> if this priority queue is empty;
         *         <tt>false</tt> otherwise
         */
        public boolean isEmpty() {
            return N == 0;
        }

        /**
         * Returns the number of keys on this priority queue.
         *
         * @return the number of keys on this priority queue
         */
        public int size() {
            return N;
        }

        /**
         * Returns a smallest key on this priority queue.
         * @return a smallest key on this priority queue
         * @throws NoSuchElementException if this priority queue is empty
         */
        public Car minp() {
            if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
            return pq[1].car_p;
        }
        public Car minm() {
            if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
            return pq[1].car_m;
        }
        

        // helper function to double the size of the heap array
        private void resize(int capacity) {
            assert capacity > N;
            DoubleCar[] temp =  new DoubleCar[capacity];
            for (int i = 1; i <= N; i++) {
                temp[i] = pq[i];
            }
            pq = temp;
        }

        /**
         * Adds a new key to this priority queue.
         *
         * @param  x the key to add to this priority queue
         */
        public void insert(Car o) {
            // double size of array if necessary
            if (N == pq.length - 1) resize(2 * pq.length);
            DoubleCar dcar=new DoubleCar(o,o);
            // add x, and percolate it up to maintain heap invariant
            pq[++N] = dcar;
            //check whether vin exist
           boolean t=pindexpq.containsKey(o.getVIN());
            if(!t){
                pindexpq.put(o.getVIN(),N);
                mindexpq.put(o.getVIN(),N);
                swimPrice(N);//swim by price to make every child and parent to feet heap difination
                swimMileage(N);//swim by mileage
                
            }else{
                System.out.println("VIN already exist, please check vin");
            }
          
            
            //assert isMinHeap();
        }

        /**
         * Removes and returns a smallest key on this priority queue.
         *
         * @return a smallest key on this priority queue
         * @throws NoSuchElementException if this priority queue is empty
         */
        /**
        public Car delMin() {
            if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
            exch(1, N);
            Car min = pq[N--];
            sink(1);
            pq[N+1] = null;         // avoid loitering and help with garbage collection
            if ((N > 0) && (N == (pq.length - 1) / 4)) resize(pq.length  / 2);
            //assert isMinHeap();
            return min;
        }
        **/
        //delete the specific car by provided VIN
        public void delCar(String VIN){
            if(!pindexpq.containsKey(VIN)){
               StdOut.print("The car not exist, try anthor VIN");
                
            }else{
             P1=pindexpq.get(VIN);
             P2=mindexpq.get(VIN);
             
             
             if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
             exch(P1, N,0);
             //N position changes, records it in pindexpq
            index(P1,N,0);
            pindexpq.remove(VIN);
            exch(P2,N,1);
            index(P2,N,1);
            
             N--;
             sinkPrice(P1);//sink by price
             sinkMileage(P2);//sink by mileage
             pq[N+1] = null;         // avoid loitering and help with garbage collection
             if(!isMinHeapP()){
                 swimPrice(N);
             }
             if(!isMinHeapM()){
                 swimMileage(N);
             }
             if ((N > 0) && (N == (pq.length - 1) / 4)) resize(pq.length  / 2);
            System.out.println("The car has been removed from system.");
            }
            
        }

       /***************************************************************************
        * Helper functions to restore the heap invariant.d
        ***************************************************************************/

        private void swimPrice(int k) {
            while (k > 1 && greater(k/2, k,0)) {
                exch(k, k/2,0);//exchange car_price position
                index(k,k/2,0);//change the pindexpq's position
                k = k/2;
            }
        }
        private void swimMileage(int k) {
            while (k > 1 && greater(k/2, k,1)) {
                exch(k, k/2,1);//exchange car_mileage position
                index(k,k/2,1);//change the pindexpq's position
                k = k/2;
            }
        }

        private void sinkPrice(int k) {
            while (2*k <= N) {
                int j = 2*k;
                if (j < N && greater(j, j+1,0)) j++;
                if (!greater(k, j,0)) break;
                exch(k, j,0);
                index(k,j,0);
                k = j;
            }
        }
        private void sinkMileage(int k) {
            while (2*k <= N) {
                int j = 2*k;
                if (j < N && greater(j, j+1,1)) j++;
                if (!greater(k, j,1)) break;
                exch(k, j,1);
                index(k,j,1);
                k = j;
            }
        }
        /***************************************************************************
         * Helper Functions to build and keep index car's postion
         **************************************************************************/
         private void index(int i,int j,int t){
             if(t==0){//put the changed position of pindexpq
                 String vin_i=pq[i].car_p.getVIN();//4-909
                 String vin_j=pq[j].car_p.getVIN();//2-2001
                 pindexpq.put(vin_i,i);
                 pindexpq.put(vin_j,j);    
             }else{//put the changed position of mindexpq
                 String vin_i=pq[i].car_m.getVIN();//4-909
                 String vin_j=pq[j].car_m.getVIN();//2-2001
                 mindexpq.put(vin_i,i);
                 mindexpq.put(vin_j,j);   
             }
             
          
         }
       /***************************************************************************
        * Helper functions for compares and swaps.
        ***************************************************************************/
        private boolean greater(int i, int j,int t) {
            if(t==0){//compare price
                Car car1= pq[i].car_p;
                Car car2=pq[j].car_p;
               return car1.getPrice()>car2.getPrice();
            }else{//compare mileage
                Car car1= pq[i].car_m;
                Car car2=pq[j].car_m;
               return car1.getMileage()>car2.getMileage();
            }
             
        }

        private void exch(int i, int j,int t) {
            if(t==0){//switch car_p
                Car swap = pq[i].car_p;
                pq[i].car_p = pq[j].car_p;
                pq[j].car_p = swap;
            }else{//switch car_m
                Car swap = pq[i].car_m;
                pq[i].car_m = pq[j].car_m;
                pq[j].car_m = swap;
                
            }
           
        }

        // is pq[1..N] a min heap?
        private boolean isMinHeapP() {
            return isMinHeapP(1);
        }
        private boolean isMinHeapM() {
            return isMinHeapM(1);
        }

        // is subtree of pq[1..N] rooted at k a min heap?
        private boolean isMinHeapP(int k) {
            if (k > N) {return true;}
            int left = 2*k, right = 2*k + 1;
            if (left  <= N && greater(k, left,0))  return false;
            if (right <= N && greater(k, right,0)) return false;
            return isMinHeapP(left) && isMinHeapP(right);
        }
        private boolean isMinHeapM(int k) {
            if (k > N) {return true;}
            int left = 2*k, right = 2*k + 1;
            if (left  <= N && greater(k, left,0))  return false;
            if (right <= N && greater(k, right,0)) return false;
            return isMinHeapM(left) && isMinHeapM(right);
        }
        //DoubleCar object to store two cars, one is ranked by price, the other is by mielage
        public class DoubleCar{
           private Car car_p;
           private Car car_m;
           private DoubleCar(Car car1,Car car2){
               car_p=car1;
               car_m=car2;
           }
        }

 /**
  * print all pindexpq and mindexpq funtion
  */
        public void printindex(){
            System.out.println("Print all object in Price index hashmap");
            Set<String> keys = pindexpq.keySet();  //get all keys
            for(String i: keys)
            {
                System.out.println(i+"--"+pindexpq.get(i));
            }
            System.out.println("Print all object in Mileage index hashmap");
            Set<String> keysm = mindexpq.keySet();  //get all keys
            for(String i: keys)
            {
                System.out.println(i+"--"+mindexpq.get(i));
            }
        }
/**
 * Update functions
 */
         public void updateColor(String vin,String color){
            
                 int pp=pindexpq.get(vin);//price position
                 int mp=mindexpq.get(vin);//mileage position
                 pq[pp].car_p.setColor(color);
                 pq[mp].car_m.setColor(color);
             
         }
         public void updatePrice(String vin,double price){
             int pp=pindexpq.get(vin);//price position
             int mp=mindexpq.get(vin);
             pq[mp].car_m.setPrice(price);
             pq[pp].car_p.setPrice(price);
             sinkPrice(pp);
             if(!isMinHeapP()){
                 swimPrice(N);
             }
              
         }
         public void updateMileage(String vin,int mileage){
             int pp=pindexpq.get(vin);
             int mp=mindexpq.get(vin);//price position
             pq[pp].car_p.setMileage(mileage);
             pq[mp].car_m.setMileage(mileage);
             sinkMileage(mp);
             if(!isMinHeapM()){
                 swimMileage(N);
             }
         }
         /**
          * Sort double PQ by one of attributes
          */
         public void sort(DoubleCar[] pq,int t) {
             int G=N;
             if(t==0){//compare based on price
            
             for (int k = G/2; k >= 1; k--)
                 sink(pq, k, G,0);
             while (G > 1) {
                 exch(pq, 1, G--,0);
                 sink(pq, 1, G,0);
             }
             }else if(t==1){
               
                 for (int k = G/2; k >= 1; k--)
                     sink(pq, k, G,1);
                 while (G > 1) {
                     exch(pq, 1, G--,1);
                     sink(pq, 1, G,1);
                 }  
                 
             }
         }
         private static void sink(DoubleCar[] pq, int k, int G,int t) {
             while (2*k <= G) {
                 int j = 2*k;
                 if (j < G && less(pq, j, j+1,t)) j++;
                 if (!less(pq, k, j,t)) break;
                 exch(pq, k, j,t);
                 k = j;
             }
         }
         private static boolean less(DoubleCar[] pq, int i, int j,int t) {
             if(t==0){//compare by price
                   return pq[i].car_p.getPrice()<pq[j].car_p.getPrice();}
             else//compare by mileage
                   return pq[i].car_m.getMileage()<pq[j].car_m.getMileage();
                     
         }

         private static void exch(DoubleCar[] pq, int i, int j,int t) {
             if(t==0){//exchange car_p
             Car swap = pq[i].car_p;
             pq[i].car_p = pq[j].car_p;
             pq[j].car_p = swap;
             }else{//exch car_m
                 Car swap = pq[i].car_m;
                 pq[i].car_m = pq[j].car_m;
                 pq[j].car_m = swap;
             }
         }

         /**
          * find lowest price or mileage car by given model and make
          */
         public Car findP(String model,String make){
               sort(pq,0);
             for(int i=1;i<=N;i++){
                
                 String md=pq[i].car_p.getModel();
                 String mk=pq[i].car_p.getMake();
               
                 if(md.equals(model)&&mk.equals(make)){
                
                    return pq[i].car_p; 
                 }
                
             }
             return null;
          
         }
         public Car findM(String model,String make){
             sort(pq,1);
             for(int i=1;i<=N;i++){
                 String md=pq[i].car_m.getModel();
                 String mk=pq[i].car_m.getMake();
                 if(md.equals(model)&&mk.equals(make)){
                    return pq[i].car_m; 
                 }
                
             }
             return null;
          
         }
         public void findAll(){

             System.out.println("--------------------------------------");
             System.out.println("All cars in double pq ranked by Price");
             System.out.println("--------------------------------------");
             for(int i=1;i<=N;i++){
                 Car tt=pq[i].car_p;
                 System.out.println("VIN:"+tt.getVIN()+" Make: "+tt.getMake()+" Model: "+tt.getModel()+" Color: "+tt.getColor()+" Mileage: "+tt.getMileage()+" Price: "+tt.getPrice());
                  
                 }

             System.out.println("--------------------------------------");
             System.out.println("All cars in double pq ranked by Mileage");

             System.out.println("--------------------------------------");
             for(int i=1;i<=N;i++){
                 Car tt=pq[i].car_m;
                 System.out.println("VIN:"+tt.getVIN()+" Make: "+tt.getMake()+" Model: "+tt.getModel()+" Color: "+tt.getColor()+" Mileage: "+tt.getMileage()+" Price: "+tt.getPrice());
                  
                 }
         
          
         }
         public void findSpe(String vin){//find specific car
             
             int pp=pindexpq.get(vin);//price position
            
             Car tt=pq[pp].car_p;
             System.out.println("VIN:"+tt.getVIN()+" Make: "+tt.getMake()+" Model: "+tt.getModel()+" Color: "+tt.getColor()+" Mileage: "+tt.getMileage()+" Price: "+tt.getPrice());
             
         
     }
        /**
         * Unit tests the <tt>MinPQ</tt> data type.
         */
        public static void main(String[] args) {
            DoublePQ minpq = new DoublePQ();
            Car car=new Car("789","Vos","Van",2000,90,"grey");
            Car car1=new Car("789","Ford","Van",2001,52,"grey");
            Car car2=new Car("791","BMW","Van",10000,500,"grey");
            Car car3=new Car("792","R","Van",909,1000,"grey");
            Car car4=new Car("793","STATA","Van",1000,49999,"grey");
            Car car5=new Car("794","Vos","Van",3000,50000,"grey");
            Car car6=new Car("795","BMW","Van",10001,51,"grey");
            Car car7=new Car("796","Vos","Van",2,50,"grey");
            
            List<Car>listcar=new ArrayList<Car>();   
            listcar.add(car);
            listcar.add(car1);
            listcar.add(car2);
            listcar.add(car3);
            listcar.add(car4);
            listcar.add(car5);
            listcar.add(car6);
            listcar.add(car7);
         //Car <Integer,Object>carinfo=new Car<Integer,Object>();
         //carinfo.put(car.getPrice(), car);
            for(int i=0;i<listcar.size();i++){
                minpq.insert(listcar.get(i));
            }
         

            Set<String> keys = minpq.pindexpq.keySet();  //get all keys
            for(String i: keys)
            {
                System.out.println(i+"--"+minpq.pindexpq.get(i));
            }
            System.out.println("print rank by mileage");
            Set<String> keysm = minpq.mindexpq.keySet();  //get all keys
            for(String i: keysm)
            {
                System.out.println(i+"--"+minpq.mindexpq.get(i));
            }

             //StdOut.print(minpq.min().getVIN());
             //StdOut.print(minpq.pindexpq.get(792));
       
            minpq.delCar("795");
            System.out.println("after delCar() ");
            System.out.println("Print Price rank");

            Set<String> keysafter = minpq.pindexpq.keySet();
            for(String i: keysafter)
            {
                System.out.println(i+"--"+minpq.pindexpq.get(i));
            }
            System.out.println("Print Mileage rank");

            Set<String> keysafterm = minpq.mindexpq.keySet();
            for(String i: keysafterm)
            {
                System.out.println(i+"--"+minpq.mindexpq.get(i));
            }
           Car tt=minpq.findP("Van", "BMW");
           System.out.println("VIN:"+tt.getVIN()+" Make: "+tt.getMake()+" Model: "+tt.getModel()+" Color: "+tt.getColor()+" Mileage: "+tt.getMileage()+" Price: "+tt.getPrice());
           Car aa=minpq.findM("Van", "Vos");
           System.out.println("VIN:"+aa.getVIN()+" Make: "+aa.getMake()+" Model: "+aa.getModel()+" Color: "+aa.getColor()+" Mileage: "+aa.getMileage()+" Price: "+aa.getPrice());
            
                     minpq.findAll();
                     minpq.findSpe("792");
           
        }

    }

