import java.util.Scanner; 
public class Main { 
   public static void main(String[] args) { 
      Scanner sc = new Scanner(System.in); 
      System.out.print("입력: "); 
      String input = sc.nextLine(); 
      perm(input.toCharArray(), 0, input.length(), input.length()); 
   } 
   public static void perm(char[] arr, int depth, int n, int k) { 
      if (depth == k) { // 한번 depth 가 k로 도달하면 사이클이 돌았음. 출력함. 
         print(arr,k); 
         return; 
      } 
      for (int i = depth; i < n; i++) { 
         swap(arr, i, depth); 
         perm(arr, depth + 1, n, k); 
         swap(arr, i, depth); 
      } 
   } 
   // 자바에서는 포인터가 없기 때문에 아래와 같이, 인덱스 i와 j를 통해 바꿔줌. 
   public static void swap(char[] arr, int i, int j) { 
      char temp = arr[i]; 
      arr[i] = arr[j]; 
      arr[j] = temp; 
   } 
   public static void print(char[] arr, int k) { 
      for (int i = 0; i < k; i++) { 
         System.out.print(arr[i]); 
      } 
      System.out.println(); 
   } 
}