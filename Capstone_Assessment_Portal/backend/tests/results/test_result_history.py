from fastapi import status
from tests.utils.encryption import encrypt_password


def test_get_result_history_success(
    client,
    student_token,
):
    """
    Verify that the logged-in student can retrieve their complete quiz result history
    """

    response = client.get(
        "/results/history",
        headers={
            "Authorization": f"Bearer {student_token}",
        },
    )

    assert response.status_code == status.HTTP_200_OK

    data = response.json()

    assert isinstance(data, list)
    assert len(data) > 0

    assert "id" in data[0]
    assert "quiz_id" in data[0]
    assert "score" in data[0]
    assert "percentage" in data[0]
    assert "result_status" in data[0]


def test_get_result_history_not_found(
    client,
):
    """
    Verify that an appropriate error is returned when the student has no result history
    """

    login = client.post(
        "/auth/login",
        json={
            "email": "newstudent@gmail.com",
            "password": encrypt_password("Student@123"),
        },
    )

    assert login.status_code == status.HTTP_200_OK

    token = login.json()["access_token"]

    response = client.get(
        "/results/history",
        headers={
            "Authorization": f"Bearer {token}",
        },
    )

    assert response.status_code == status.HTTP_404_NOT_FOUND