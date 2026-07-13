from fastapi import status
from tests.utils.encryption import encrypt_password


def test_get_latest_result_success(
    client,
    student_token,
):
    """
    Verify that the latest quiz result is returned successfully for the logged-in student
    """

    response = client.get(
        "/results/latest",
        headers={
            "Authorization": f"Bearer {student_token}",
        },
    )

    assert response.status_code == status.HTTP_200_OK

    data = response.json()

    assert data["student_id"] is not None
    assert data["attempt_id"] is not None
    assert data["quiz_id"] is not None
    assert isinstance(
        data["questions"],
        list,
    )


def test_get_latest_result_not_found(
    client,
):
    """
    Verify that an appropriate error is returned when the student has no quiz results
    """

    login = client.post(
        "/auth/login",
        json={
            "email": "newstudent@gmail.com",
            "password": encrypt_password("Student@123"),
        },
    )

    token = login.json()["access_token"]

    response = client.get(
        "/results/latest",
        headers={
            "Authorization": f"Bearer {token}",
        },
    )

    assert response.status_code == status.HTTP_404_NOT_FOUND