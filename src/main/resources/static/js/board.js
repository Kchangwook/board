const sweetAlert = {
    errorAlert (title = '',  text = '') {
        Swal.fire({
            icon: 'error',
            title: title,
            text: text
        });
    },
    successAlert (title = '', text = '') {
        Swal.fire({
            icon: 'success',
            title: title,
            text: text
        });
    }
};