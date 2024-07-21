Để Gửi API xuống tầng backend, thì API cần phải có đầy đủ các yếu tố:
- HTTP Methods (POST, GET, ...)
- URL ==> endpoint
- Headers ==> phần này được cấu hình, tạo mới ở HttpUtilService
- Body ==> Là các DTO
- Params ==> là các dữ liệu được chuyển thành keyword ở dưới backend;

Storage This Web Storage API interface provides access to a particular domain's 
session or local storage. It allows, for example, the addition, modification, 
or deletion of stored data items.
- Dữ liệu trong localStorage được lưu trữ cục bộ trên máy tính của người dùng.
- Nó bị ràng buộc bởi trình duyệt cụ thể và tên miền cụ thể. Dữ liệu không bị 
xóa khi đóng trình duyệt hoặc tắt máy tính, và sẽ tồn tại cho đến khi được xóa thủ công.
- Mỗi trình duyệt có một không gian lưu trữ riêng cho localStorage. 