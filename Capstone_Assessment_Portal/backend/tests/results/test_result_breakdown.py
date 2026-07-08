from fastapi import status


def test_get_result_breakdown_success(
    client,
    student_token,
):
    """
    Verify that a student can retrieve the detailed breakdown of a quiz result
    """

    latest = client.get(
        "/results/latest",
        headers={
            "Authorization": f"Bearer {student_token}",
        },
    )

    result_id = latest.json()["id"]

    response = client.get(
        f"/results/{result_id}",
        headers={
            "Authorization": f"Bearer {student_token}",
        },
    )

    assert response.status_code == status.HTTP_200_OK

    data = response.json()

    assert data["id"] == result_id
    assert isinstance(data["questions"], list)
    assert len(data["questions"]) > 0

    assert "question_id" in data["questions"][0]
    assert "selected_answer" in data["questions"][0]
    assert "correct_answer" in data["questions"][0]
    assert "is_correct" in data["questions"][0]


def test_get_result_breakdown_invalid_result(
    client,
    student_token,
):
    """
    Verify that requesting a non-existing result returns 404
    """

    response = client.get(
        "/results/686bcb8d8d8d8d8d8d8d8d8d",
        headers={
            "Authorization": f"Bearer {student_token}",
        },
    )

    assert response.status_code == status.HTTP_404_NOT_FOUND


def test_student_cannot_view_another_student_result(
    client,
    student_token,
):
    """
    Verify that a student cannot access another student's quiz result
    """

    login = client.post(
        "/auth/login",
        json={
            "email": "newstudent@gmail.com",
            "password": "Student@123",
        },
    )

    assert login.status_code == status.HTTP_200_OK

    other_student_token = login.json()["access_token"]

    latest = client.get(
        "/results/latest",
        headers={
            "Authorization": f"Bearer {student_token}",
        },
    )

    assert latest.status_code == status.HTTP_200_OK

    result_id = latest.json()["id"]

    response = client.get(
        f"/results/{result_id}",
        headers={
            "Authorization": f"Bearer {other_student_token}",
        },
    )

    assert response.status_code == status.HTTP_404_NOT_FOUND